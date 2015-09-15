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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import uk.ac.ed.portlets.almaportlet.data.Fine;
import uk.ac.ed.portlets.almaportlet.data.LoanItem;
import uk.ac.ed.portlets.almaportlet.data.RequestItem;
import uk.ac.ed.portlets.almaportlet.util.LibraryObjBuilder;

/**
 *
 * @author hsun1
 */
@ContextConfiguration(locations={"classpath:applicationContext-test.xml"})
public class AlmaReaderServiceTest extends AbstractJUnit4SpringContextTests {
    
    @Autowired 
    AlmaReaderService almaReaderService;
    
    @Test
    public void testProcessFines() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><fees total_record_count=\"4\" total_sum=\"415.0\" currency=\"USD\"><fee link=\"/almaws/v1/users/johns/fees/950645520000121\"><id>950645520000121</id><type desc=\"Overdue fine\">OVERDUEFINE</type><status desc=\"Active\">ACTIVE</status><balance>400.0</balance><original_amount>400.0</original_amount><creation_time>2014-10-21T08:17:12.450Z</creation_time><comment>Date generated: 10/21/2014, Due: 06/23/2014, Fine Policy: Overdue Fine for All Hours, Action: Renewed</comment><title>History</title></fee><fee link=\"/almaws/v1/users/johns/fees/950645580000121\"><id>950645580000121</id><type desc=\"Renew fee\">RENEWFEE</type><status desc=\"Active\">ACTIVE</status><balance>10.0</balance><original_amount>10.0</original_amount><creation_time>2014-10-21T08:17:12.709Z</creation_time><title>History</title></fee><fee link=\"/almaws/v1/users/johns/fees/711924990000121\"><id>711924990000121</id><type desc=\"Overdue fine\">OVERDUEFINE</type><status desc=\"Active\">ACTIVE</status><balance>55.0</balance><original_amount>77.0</original_amount><creation_time>2014-07-15T08:40:32.630Z</creation_time><comment>Date generated: 15/07/2014, Due: 23/06/2014, Fine Policy: Overdue Fine for All Hours, Action: Lost with overdue charge</comment><title>History</title><transactions><transaction><type desc=\"Payment\">PAYMENT</type><amount>22.0</amount><created_by>Ex Libris</created_by><transaction_time>2014-10-20T13:26:54.144Z</transaction_time></transaction></transactions></fee><fee link=\"/almaws/v1/users/johns/fees/950644660000121\"><id>950644660000121</id><type desc=\"Credit\">CREDIT</type><status desc=\"Active\">ACTIVE</status><balance>-50.0</balance><original_amount>-50.0</original_amount><creation_time>2014-10-21T07:45:03.188Z</creation_time><title>History</title></fee></fees>";                
        ArrayList<Hashtable<String, String>> fines = almaReaderService.parseXML(xml);        
        List<Fine> results = LibraryObjBuilder.buildFines(fines);   
        
        String expected = "History";
        String result = results.get(0).getItemTitle();
        assertEquals(expected,result);
    }    
  
    @Test
    public void testProcessLoans() throws Exception {
        String xml = "<item_loans><item_loan><loan_id>694674780000121</loan_id><circ_desk desc=\"Main Circulation Desk\">MAIN_CIRC</circ_desk><library desc=\"Main Library\">ULINC</library><user_id>ID1234</user_id><item_barcode>000237055710000121</item_barcode><due_date>2014-06-23T14:00:00.000Z</due_date><loan_status>Active</loan_status><loan_date>2014-06-22T11:00:28.377Z</loan_date><process_status>NORMAL</process_status><title>History</title><description>v2</description><location_code name=\"Location 1\">LOC_1</location_code></item_loan><item_loan><loan_id>911153710000121</loan_id><circ_desk desc=\"Resource Sharing Desk\">RES_DESK</circ_desk><library desc=\"Resource Sharing Library\">RES_SHARE</library><user_id>ID1234</user_id><item_barcode>RS-EXLDEV10009782</item_barcode><due_date>2015-05-19T13:00:00.000Z</due_date><loan_status>Active</loan_status><loan_date>2014-08-19T07:49:39.628Z</loan_date><process_status>NORMAL</process_status><title>doctor</title><location_code name=\"Borrowing Resource Sharing Requests\">OUT_RS_REQ</location_code></item_loan></item_loans>";       
        ArrayList<Hashtable<String, String>> loads = almaReaderService.parseXML(xml);
        List<LoanItem> results = LibraryObjBuilder.buildLoans(loads);         
        
        String expected = "History";
        String result = results.get(0).getTitle();
        assertEquals(expected,result);
    }      
    
    @Test
    public void testProcessRequests() throws Exception {
        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><user_requests total_record_count=\"1\"><user_request><request_id>83013520000121</request_id><request_type>HOLD</request_type><title>Test title</title><pickup_location>Burns</pickup_location><pickup_location_type>LIBRARY</pickup_location_type><pickup_location_library>BURNS</pickup_location_library><material_type>BK</material_type><request_status>Not Started</request_status><request_date>2013-11-12Z</request_date></user_request></user_requests>";
        ArrayList<Hashtable<String, String>> requests = almaReaderService.parseXML(xml);
        List<RequestItem> results = LibraryObjBuilder.buildRequests(requests);
        
        String expected = "Test title";
        String result = results.get(0).getItemTitle();
        assertEquals(expected,result);
    }  
     
   
}
