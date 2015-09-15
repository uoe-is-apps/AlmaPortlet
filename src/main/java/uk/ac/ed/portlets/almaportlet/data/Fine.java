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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a fine issued to a patron.
 * 
 *  &lt;fine href="http://127.0.0.1:7014/vxws/patron/252470/circulationActions/debt/fines/1517083?patron_homedb=1@EDDB20020903165307"&gt;
 *    &lt;fineId&gt;1517083&lt;/fineId&gt;
 *    &lt;fineDate&gt;2013-10-14&lt;/fineDate&gt;
 *    &lt;itemTitle&gt;One flew over the cuckoo's nest / a novel by Ken Kesey.&lt;/itemTitle&gt;
 *    &lt;fineType&gt;Overdue&lt;/fineType&gt;
 *    &lt;amount&gt;GBP 5.00&lt;/amount&gt;
 *    &lt;dbKey&gt;EDDB20020903165307&lt;/dbKey&gt;
 *    &lt;dbName&gt;Edinburgh University Librar&gt;&lt;/dbName&gt;
 *  &lt;/fine&gt;
 *
 */
@XmlRootElement(name = "fine")
@XmlAccessorType(XmlAccessType.FIELD)
public class Fine {
    @XmlAttribute(name = "href")
    private String href;
    
    @XmlElement(name = "fineId")
    private String fineId;
    
    @XmlElement(name = "fineDate")
    private String fineDate;
    
    @XmlElement(name = "itemTitle")
    private String itemTitle;
    
    @XmlElement(name = "fineType")
    private String fineType;
    
    @XmlElement(name = "amount")
    private String amount;
    
    @XmlElement(name = "dbKey")
    private String dbKey;
    
    @XmlElement(name = "dbName")
    private String dbName;

    /**
     * @return the ID of the fine.
     */
    public String getFineId() {
        return fineId;
    }

    /**
     * @return the date the fine was issued on.
     */
    public String getFineDate() {
        return fineDate;
    }
    
    @JsonProperty("fineDateConverted")
    public String getFineDateConverted() throws ParseException{
        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(fineDate);         
        return new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH).format(date);
    }

    /**
     * @return the itemTitle
     */
    public String getItemTitle() {
        return itemTitle;
    }

    /**
     * @return the fineType
     */
    public String getFineType() {
        return fineType;
    }

    /**
     * @return the amount
     */
    public String getAmount() {
        return amount;
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
     * @return the href
     */
    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setFineId(String fineId) {
        this.fineId = fineId;
    }

    public void setFineDate(String fineDate) {
        this.fineDate = fineDate;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public void setFineType(String fineType) {
        this.fineType = fineType;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public void setDbKey(String dbKey) {
        this.dbKey = dbKey;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }
    
    
}
