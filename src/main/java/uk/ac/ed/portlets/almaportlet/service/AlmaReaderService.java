/*
 * Copyright 2015 Jasig.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.ed.portlets.almaportlet.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import uk.ac.ed.portlets.almaportlet.data.Fine;
import uk.ac.ed.portlets.almaportlet.data.LoanItem;
import uk.ac.ed.portlets.almaportlet.data.RequestItem;
import uk.ac.ed.portlets.almaportlet.util.LibraryObjBuilder;



/**
 *
 * @author hsun1
 */
@Service
public class AlmaReaderService {
    
    private static final String TYPE_LOAN = "loans";
    private static final String TYPE_REQUEST = "requests";
    private static final String TYPE_FINE = "fees";
    
    private final Log logger = LogFactory.getLog(AlmaReaderService.class);
	    
    @Value("${applicationKey}")
    private String applicationKey;
    
    @Value("${almaBaseURL}")
    private String almaBaseURL;

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }

    public String getAlmaBaseURL() {
        return almaBaseURL;
    }

    public void setAlmaBaseURL(String almaBaseURL) {
        this.almaBaseURL = almaBaseURL;
    }
   
    /**
     * Given userid and type of request (fine, loan, request) get xml from web service
     * @param userid user id
     * @param type type of request (fine, loan, request)
     * @return xml wraps the data
     */
    private String readRestfulService(String userid, String type, String status) throws Exception{
        logger.debug("applicationKey --> " + applicationKey);
        logger.debug("almaBaseURL --> " + almaBaseURL);

        try{
            String endpoint = almaBaseURL + userid + "/" + type;
            
            logger.debug("call alma --> " + endpoint);
        
            StringBuilder urlBuilder = new StringBuilder(endpoint);
            urlBuilder.append("?");
            urlBuilder.append(URLEncoder.encode("user_id_type","UTF-8") + "=" + URLEncoder.encode("all_unique", "UTF-8") + "&");
            urlBuilder.append(URLEncoder.encode("status","UTF-8") + "=" + URLEncoder.encode(status, "UTF-8") + "&");
            if(type.equals(TYPE_LOAN) ){
                urlBuilder.append(URLEncoder.encode("limit","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8") + "&");
            }
            urlBuilder.append(URLEncoder.encode("apikey","UTF-8") + "=" + URLEncoder.encode(applicationKey, "UTF-8"));
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            logger.debug("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();
         
            String result = sb.toString();
            logger.debug("result --> " + result);            
            if(type.equals(TYPE_FINE)){
                if(result.contains("<fees")){
                    return result;
                }
            }else if(type.equals(TYPE_LOAN) ){
                if(result.contains("<item_loans")){
                    return result;
                }
            }else if(type.equals(TYPE_REQUEST)){        
                if(result.contains("<user_requests")){
                    return result;
                }
            }
                
            return "";
        }catch(Exception e){
            return "";
        }
    }
    
    /**
     * Parse xml as a list of hash table which wraps library object
     * @param xml xml as string from the web service
     * @return a list of hash table which wraps library object
     */    
    public ArrayList<Hashtable<String, String>> parseXML(String xml) throws Exception{
        ArrayList<Hashtable<String, String>> list = new ArrayList<Hashtable<String, String>>();
  
        Document document = DocumentHelper.parseText(xml);        
        Element root = document.getRootElement();

        for ( Iterator i = root.elementIterator(); i.hasNext(); ) {
            Element parent = (Element) i.next();
           
            Hashtable<String, String> table = new Hashtable<String, String>();
            for ( Iterator r = parent.elementIterator(); r.hasNext(); ) {
                Element child = (Element) r.next();
                table.put(child.getName(), child.getStringValue());
            }
            list.add(table);
        }
        logger.debug("parseXML --> " + list);         
        return list;
    }    
        
    /**
     * Given an user id get a list of Fine object
     * @param userid user id
     * @return a list of Fine object
     */        
    public List<Fine> processFines(String userid) throws Exception {
        String xml = readRestfulService(userid, TYPE_FINE, "ACTIVE");   
        if(xml.equals("")) return new ArrayList<Fine>();
        ArrayList<Hashtable<String, String>> fines = parseXML(xml);        
        List<Fine> results = LibraryObjBuilder.buildFines(fines);        
        return results;
    } 
    
    /**
     * Given an user id get a list of LoanItem object
     * @param userid user id
     * @return a list of LoanItem object
     */      
    public List<LoanItem> processLoans(String userid) throws Exception {
        String xml = readRestfulService(userid, TYPE_LOAN, "ACTIVE");     
        if(xml.equals("")) return new ArrayList<LoanItem>();
        ArrayList<Hashtable<String, String>> loads = parseXML(xml);
        List<LoanItem> results = LibraryObjBuilder.buildLoans(loads);        
        return results;
    }    
    
    /**
     * Given an user id get a list of Callslip object
     * @param userid user id
     * @return a list of Callslip object
     */     
    public List<RequestItem> processRequests(String userid) throws Exception {
        String xml = readRestfulService(userid, TYPE_REQUEST, "active");    
        if(xml.equals("")) return new ArrayList<RequestItem>();
        ArrayList<Hashtable<String, String>> requests = parseXML(xml);
        List<RequestItem> results = LibraryObjBuilder.buildRequests(requests);
        return results;
    }      

}
