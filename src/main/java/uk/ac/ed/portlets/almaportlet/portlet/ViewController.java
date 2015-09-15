package uk.ac.ed.portlets.almaportlet.portlet;
/*
 * retrieves user attributes from Portlet and stores them in session [without validation]
 * calls VIEW to render
 * 
 */

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

import uk.ac.ed.portlets.almaportlet.json.UserInfo;


@Controller
@RequestMapping("VIEW")
public class ViewController {

	protected final Log logger = LogFactory.getLog(ViewController.class);
	        
    @Value("${basicLoginUrl}")
	private String basicLoginUrl;

	@RenderMapping
    public ModelAndView showMainView(final RenderRequest request, final RenderResponse response){

		final PortletSession session = request.getPortletSession();

        ModelAndView modelAndView = new ModelAndView("main");

        modelAndView.addObject("basicLoginUrl",basicLoginUrl);
                
		UserInfo userInfo = (UserInfo) session.getAttribute(UserInfo.SESSION_ATTR, PortletSession.APPLICATION_SCOPE);
		
		if(userInfo == null){
			
			@SuppressWarnings(value = "unchecked")
			final Map<String,String> userInfoMap = (Map<String,String>) request.getAttribute(PortletRequest.USER_INFO);
			
			String barcode = userInfoMap.get("librarynumber");
		    String surname = userInfoMap.get("sn");
				 
		    userInfo = new UserInfo(userInfoMap.get("uid"), barcode, surname);// non-validated UserInfo

		    session.setAttribute(UserInfo.SESSION_ATTR, userInfo, PortletSession.APPLICATION_SCOPE);
		}

	    return modelAndView;
	}
}
