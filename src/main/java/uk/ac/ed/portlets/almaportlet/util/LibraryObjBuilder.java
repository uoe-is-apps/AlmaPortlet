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
package uk.ac.ed.portlets.almaportlet.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import uk.ac.ed.portlets.almaportlet.data.Fine;
import uk.ac.ed.portlets.almaportlet.data.LoanItem;
import uk.ac.ed.portlets.almaportlet.data.RequestItem;

/**
 *
 * @author hsun1
 */
public class LibraryObjBuilder {

    public static List<RequestItem> buildRequests(ArrayList<Hashtable<String, String>> requests){
        List<RequestItem> requestItems = new ArrayList<RequestItem>();
         for(Hashtable<String, String> request:requests){
            requestItems.add(buildRequest(request));
         }    

         return requestItems;
    }
    
    public static List<LoanItem> buildLoans(ArrayList<Hashtable<String, String>> loans){
        List<LoanItem> results = new ArrayList<LoanItem>();        
        for(Hashtable<String, String> loan:loans){
            results.add(buildLoan(loan));
        }
        return results;        
    }
    
    public static List<Fine> buildFines(ArrayList<Hashtable<String, String>> fines){
        List<Fine> results = new ArrayList<Fine>();        
        for(Hashtable<String, String> fine:fines){
            results.add(buildFine(fine));
        }
        return results;       
    }   
    
    
    private static RequestItem buildRequest(Hashtable<String, String> table){
         /*
         (1). On MyEd, it displays:
         Item	        Pickup Location	          Status
         itemTitle	pickupLocation	          Position queue  Position Expires 14-10-2013  
         
         (2). From old web service, it builds above as:
         List<Callslip> callslips = new ArrayList<Callslip>();
         Callslip callslip = new Callslip();
         
         List<RequestItem> requestItems = new ArrayList<RequestItem>();
         RequestItem requestItem = new RequestItem();
         requestItem.setItemId("itemId");
         requestItem.setHoldRecallId("holdRecallId");
         requestItem.setReplyNote("replyNote");
         requestItem.setStatus("status");
         requestItem.setStatusText("statusText");
         requestItem.setHoldType("holdType");
         requestItem.setItemTitle("itemTitle");
         requestItem.setExpiredDate("2013-10-14");
         requestItem.setDbKey("dbKey");
         requestItem.setDbName("dbName");
         requestItem.setQueuePosition("queuePosition");
         requestItem.setPickupLocation("pickupLocation");
         requestItem.setPickupLocationCode("pickupLocationCode");
         requestItem.setInstName("instName");
         requestItems.add(requestItem);

         callslip.setRequestItems(requestItems);
         callslips.add(callslip);
         
         summary.setTotalRequests(callslips.size());
         summary.setCallslips(callslips);
 
         (3). New alma web service, it has these fields:
         key ----------- request_id
         val - 83013520000121
         key ----------- request_type
         val - HOLD
         key ----------- title
         val - Test title
         key ----------- pickup_location
         val - Burns
         key ----------- pickup_location_type
         val - LIBRARY
         key ----------- pickup_location_library
         val - BURNS
         key ----------- material_type
         val - BK
         key ----------- request_status
         val - Not Started
         key ----------- request_date
         val - 2013-11-12Z 
         */
                
        
         String itemTitle = handleNoTitle(table.get("title"));
         String pickupLocation = table.get("pickup_location");
         String expiredDate = table.get("request_date").substring(0, 10); //not available from new service
         String queuePosition = ""; //not available from new service
         RequestItem requestItem = new RequestItem();
         requestItem.setItemTitle(itemTitle);
         requestItem.setPickupLocation(pickupLocation);
         requestItem.setQueuePosition(queuePosition);
         requestItem.setExpiredDate(expiredDate);
        
         return requestItem;
    }

    private static LoanItem buildLoan(Hashtable<String, String> table){
         /*
         (1). On MyEd, it displays:
         Item	Type	    Status          Due Date
         title	itemtype    statusText      13-10-2014 23:59   
         
         (2). From old web service, it builds above as:
         List<LoanItem> loanItems = new ArrayList<LoanItem>();
         LoanItem loan = new LoanItem();
         
         loan.setCanRenew("canRenew");
         loan.setHref("href");
         loan.setItemId("itemId");
         loan.setItemBarcode("itemBarcode");
         loan.setDueDate("2014-10-13 23:59");
         loan.setOrigDueDate("2014-10-13 23:59");
         loan.setTodaysDate("2014-10-13 23:59");
         loan.setTitle("title");
         loan.setAuthor("author");
         loan.setLocation("location");
         loan.setLocationCode("locationCode");
         loan.setCallNumber("callNumber");
         loan.setStatusCode("statusCode");
         loan.setStatusText("statusText");
         loan.setItemtype("itemtype");
         loan.setDbKey("dbKey");
         loan.setDbName("dbName");

         loanItems.add(loan);
         summary.setTotalLoans(loanItems.size());
         summary.setLoanItems(loanItems);              
 
         (3). New alma web service, it has these fields:
         key ----------- loan_id
         val - 911153710000121
         key ----------- circ_desk
         val - RES_DESK
         key ----------- library
         val - RES_SHARE
         key ----------- user_id
         val - ID1234
         key ----------- item_barcode
         val - RS-EXLDEV10009782
         key ----------- due_date
         val - 2015-05-19T13:00:00.000Z
         key ----------- loan_status
         val - Active
         key ----------- loan_date
         val - 2014-08-19T07:49:39.628Z
         key ----------- process_status
         val - NORMAL
         key ----------- title
         val - doctor
         key ----------- location_code
         val - OUT_RS_REQ
         */        
        
         String title = handleNoTitle(table.get("title"));
         String type = ""; //not available from new service
         String statusText = table.get("loan_status");
         String dueDate = handleHour(table.get("due_date"));
         LoanItem loan = new LoanItem();
         loan.setTitle(title);
         loan.setItemtype(type);
         loan.setStatusText(statusText);
         loan.setDueDate(dueDate);
         return loan;
    }   

    
    private static Fine buildFine(Hashtable<String, String> table){
         /*
         (1). On MyEd, it displays:
         Date	        Item	        Fee Posting/Type	Fee
         14-10-2013	itemTitle	Overdue	                (pound sign) 5.00              
         
         (2). From old web service, it builds above as:
         List<Fine> fines = new ArrayList<Fine>();
         Fine fine = new Fine();
         fine.setHref("http://127.0.0.1:7014/vxws/patron/252470/circulationActions/debt/fines/1517083?patron_homedb=1@EDDB20020903165307");
         fine.setFineId("1517083");
         fine.setFineDate("2013-10-14");
         fine.setItemTitle("itemTitle");
         fine.setFineType("Overdue");
         fine.setAmount("GBP 5.00");
         fine.setDbKey("EDDB20020903165307");
         fine.setDbName("Edinburgh University Library");    
         fines.add(fine);         
         summary.setTotalFines(fines.size());
         summary.setFines(fines);

         (3). New alma web service, it has these fields:
         key ----------- id
         val - 950644660000121
         key ----------- type
         val - CREDIT
         key ----------- status
         val - ACTIVE
         key ----------- balance
         val - -50.0
         key ----------- original_amount
         val - -50.0
         key ----------- creation_time
         val - 2014-10-21T07:45:03.188Z
         key ----------- title
         val - History
         */        
        
         String itemTitle = handleNoTitle(table.get("title"));
         String fineType = table.get("type");
         String fineDate = table.get("creation_time");
         String amount = "GBP" + handleEndingZero(table.get("original_amount"));
         Fine fine = new Fine();
         fine.setFineDate(fineDate);
         fine.setItemTitle(itemTitle);
         fine.setFineType(fineType);
         fine.setAmount(amount);
         return fine;
    }    
    
    private static String handleEndingZero(String amount){
        if(amount == null) return "";
        if(amount.substring( amount.indexOf(".") + 1 , amount.length()).length() == 1){
            return amount + "0";
        }else{
            return amount;
        }
    }

    private static String handleHour(String date){
         try {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date parsedDate = sf.parse(date.substring(0, 16).replace("T", " "));


            Calendar cal = Calendar.getInstance();
            cal.setTime(parsedDate);
            cal.add(Calendar.HOUR_OF_DAY, 1);
            return sf.format(cal.getTime());
        } catch (Exception e) {
            return "";
        }
    }        
    
    private static String handleNoTitle(String title){
        if(title == null || title.equals("")){
            return "no title";
        }else{
            return title;
        }
    }
}
