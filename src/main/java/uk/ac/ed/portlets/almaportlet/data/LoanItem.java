/*
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package uk.ac.ed.portlets.almaportlet.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name = "loan")
@XmlAccessorType(XmlAccessType.FIELD)
public class LoanItem {

    @XmlAttribute(name = "canRenew")
    private String canRenew;

    @XmlAttribute(name = "href")
    private String href;

    @XmlElement(name = "itemId")
    private String itemId;
    
    @XmlElement(name = "itemBarcode")
    private String itemBarcode;
    
    @XmlElement(name = "dueDate")
    private String dueDate;
    
    @XmlElement(name = "origDueDate")
    private String origDueDate;
    
    @XmlElement(name = "todaysDate")
    private String todaysDate;
    
    @XmlElement(name = "title")
    private String title;
    
    @XmlElement(name = "author")
    private String author;
    
    @XmlElement(name = "location")
    private String location;
    
    @XmlElement(name = "locationCode")
    private String locationCode;
    
    @XmlElement(name = "callNumber")
    private String callNumber;
    
    @XmlElement(name = "statusCode")
    private String statusCode;
    
    @XmlElement(name = "statusText")
    private String statusText;
    
    @XmlElement(name = "itemtype")
    private String itemtype;
    
    @XmlElement(name = "dbKey")
    private String dbKey;
    
    @XmlElement(name = "dbName")
    private String dbName;

    /**
     * @return the canRenew
     */
    public String getCanRenew() {
        return canRenew;
    }

    /**
     * @return the href
     */
    public String getHref() {
        return href;
    }

    /**
     * @return the itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * @return the itemBarcode
     */
    public String getItemBarcode() {
        return itemBarcode;
    }

    /**
     * @return the dueDate
     */
    public String getDueDate() {
        return dueDate;
    }
    
    @JsonProperty("dueDateConverted")
    public String getDueDateConverted() throws ParseException{
        Date date = new SimpleDateFormat("yyyy-MM-ddHH:mm", Locale.ENGLISH).parse(dueDate);         
        return new SimpleDateFormat("dd-MM-yyyy HH:mm",Locale.ENGLISH).format(date);
    }

    /**
     * @return the origDueDate
     */
    public String getOrigDueDate() {
        return origDueDate;
    }

    /**
     * @return the todaysDate
     */
    public String getTodaysDate() {
        return todaysDate;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the location
     */
    public String getLocation() {
        return location;
    }

    /**
     * @return the locationCode
     */
    public String getLocationCode() {
        return locationCode;
    }

    /**
     * @return the callNumber
     */
    public String getCallNumber() {
        return callNumber;
    }

    /**
     * @return the statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * @return the statusText
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * @return the itemtype
     */
    public String getItemtype() {
        return itemtype;
    }

    /**
     * @return the dbKey
     */
    public String getDbKey() {
        return dbKey;
    }

    /**
     * @return the dbName
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param canRenew the canRenew to set
     */
    public void setCanRenew(String canRenew) {
        this.canRenew = canRenew;
    }

    /**
     * @param href the href to set
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * @param itemBarcode the itemBarcode to set
     */
    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    /**
     * @param dueDate the dueDate to set
     */
    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    /**
     * @param origDueDate the origDueDate to set
     */
    public void setOrigDueDate(String origDueDate) {
        this.origDueDate = origDueDate;
    }

    /**
     * @param todaysDate the todaysDate to set
     */
    public void setTodaysDate(String todaysDate) {
        this.todaysDate = todaysDate;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @param location the location to set
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @param locationCode the locationCode to set
     */
    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    /**
     * @param callNumber the callNumber to set
     */
    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @param statusText the statusText to set
     */
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    /**
     * @param itemtype the itemtype to set
     */
    public void setItemtype(String itemtype) {
        this.itemtype = itemtype;
    }

    /**
     * @param dbKey the dbKey to set
     */
    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    /**
     * @param dbName the dbName to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
}
