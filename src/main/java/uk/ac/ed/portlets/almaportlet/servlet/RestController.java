package uk.ac.ed.portlets.almaportlet.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ed.portlets.almaportlet.data.Fine;
import uk.ac.ed.portlets.almaportlet.data.LoanItem;
import uk.ac.ed.portlets.almaportlet.data.RequestItem;
import uk.ac.ed.portlets.almaportlet.json.PatronAccountSummary;
import uk.ac.ed.portlets.almaportlet.json.UserInfo;
import uk.ac.ed.portlets.almaportlet.service.AlmaReaderService;


@Controller
public class RestController {
    public static final long MAX_AGE_MILLIS = 30 * 60 * 1000L; // 30 minutes
    public static final String SESSION_ATTR_CACHE_EXPIRY = "uk.ac.ed.portlets.mylibraryinfo.expiry";
    public static final String SESSION_ATTR_CACHE_ETAG = "uk.ac.ed.portlets.mylibraryinfo.etag";

    protected final Log logger = LogFactory.getLog(RestController.class);
    private AlmaReaderService almaReaderService;

    public AlmaReaderService getAlmaReaderService() {
        return almaReaderService;
    }

    @Autowired(required = true)
    public void setAlmaReaderService(AlmaReaderService almaReaderService) {
        this.almaReaderService = almaReaderService;
    }
             
    @RequestMapping(value="/summary", method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public PatronAccountSummary getPatronAccountSummary(HttpServletRequest request, HttpServletResponse response) throws Throwable {
    	final HttpSession session = request.getSession(false);

        logger.debug("start getting lib obj -> [" + request.getRemoteUser() + "]");
        
        String userid;
        
    	UserInfo userInfo = (UserInfo) session.getAttribute(UserInfo.SESSION_ATTR);

    	if (userInfo == null){
            userid = request.getRemoteUser();
            logger.debug("userInfo is null --> from remote user --> " + userid);
    	}else{
            userid = userInfo.getUun();
            logger.debug("userInfo is not null in session --> from userInfo --> " + userid);
        }

        if (userid == null)
        {
            throw new ServletException("Unable to identify user.");
        }
        
        /* Check if the page contents the remote system has matches the version
         * we last sent.
         */
        final String ifNoneMatch = request.getHeader("If-None-Match");
        final String existingETag = (String)session.getAttribute(SESSION_ATTR_CACHE_ETAG);
        final Date existingExpiry = (Date)session.getAttribute(SESSION_ATTR_CACHE_EXPIRY);
        if (null != ifNoneMatch
            && null != existingETag
            && null != existingExpiry
            && ifNoneMatch.equals(existingETag)
            && System.currentTimeMillis() < existingExpiry.getTime()) {
            response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
            return null;
        }

    	PatronAccountSummary summary = new PatronAccountSummary();

        logger.debug("almaReaderService is null --> " + (almaReaderService == null));
                
        List<Fine> fines = almaReaderService.processFines(userid);
        List<LoanItem> loans =  almaReaderService.processLoans(userid);
        List<RequestItem> requestItems = almaReaderService.processRequests(userid);
        
        logger.debug("fines --> " + fines.size());
        logger.debug("loans --> " + loans.size());
        logger.debug("callslips --> " + requestItems.size());
        
        summary.setTotalFines(fines.size());
        summary.setFines(fines);

        summary.setTotalLoans(loans.size());
        summary.setLoanItems(loans);
 
        summary.setTotalRequests(requestItems.size());
        summary.setRequestItems(requestItems);


        final String eTag = "\"" + summary.hashCode() + "\"";
        final Date expiry = new Date(System.currentTimeMillis() + MAX_AGE_MILLIS);
        
        session.setAttribute(SESSION_ATTR_CACHE_ETAG, eTag);
        session.setAttribute(SESSION_ATTR_CACHE_EXPIRY, expiry);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setHeader("Cache-Control", "must-revalidate");
        response.setHeader("ETag", eTag);
        
    	return summary;
    }
    
}
