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
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="requestItem")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestItem {
    @XmlElement(name = "itemId")
    private String itemId;
    
    @XmlElement(name = "holdRecallId")
    private String holdRecallId;
    
    @XmlElement(name = "replyNote")
    private String replyNote;
    
    @XmlElement(name = "status")
    private String status;
    
    @XmlElement(name = "statusText")
    private String statusText;
    
    @XmlElement(name = "holdType")
    private String holdType;
    
    @XmlElement(name = "itemTitle")
    private String itemTitle;
    
    @XmlElement(name = "expiredDate")
    private String expiredDate;
    
    @XmlElement(name = "dbKey")
    private String dbKey;
    
    @XmlElement(name = "dbName")
    private String dbName;
    
    @XmlElement(name = "queuePosition")
    private String queuePosition;
    
    @XmlElement(name = "pickupLocation")
    private String pickupLocation;
    
    @XmlElement(name = "pickupLocationCode")
    private String pickupLocationCode;
    
    @XmlElement(name = "instName")
    private String instName;

    /**
     * @return the itemId
     */
    public String getItemId() {
        return itemId;
    }

    /**
     * @return the holdRecallId
     */
    public String getHoldRecallId() {
        return holdRecallId;
    }

    /**
     * @return the replyNote
     */
    public String getReplyNote() {
        return replyNote;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @return the statusText
     */
    public String getStatusText() {
        return statusText;
    }

    /**
     * @return the holdType
     */
    public String getHoldType() {
        return holdType;
    }

    /**
     * @return the itemTitle
     */
    public String getItemTitle() {
        return itemTitle;
    }

    /**
     * @return the expiredDate
     */
    public String getExpiredDate() {
        return expiredDate;
    }
    
    @JsonProperty("expiredDateConverted")
    public String getExpiredDateConverted() throws ParseException
    {
        Date date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).parse(expiredDate);         
        return new SimpleDateFormat("dd-MM-yyyy",Locale.ENGLISH).format(date);
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
     * @return the queuePosition
     */
    public String getQueuePosition() {
        return queuePosition;
    }

    /**
     * @return the pickupLocation
     */
    public String getPickupLocation() {
        return pickupLocation;
    }

    /**
     * @return the pickupLocationCode
     */
    public String getPickupLocationCode() {
        return pickupLocationCode;
    }

    /**
     * @return the instName
     */
    public String getInstName() {
        return instName;
    }

    /**
     * @param itemId the itemId to set
     */
    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * @param holdRecallId the holdRecallId to set
     */
    public void setHoldRecallId(String holdRecallId) {
        this.holdRecallId = holdRecallId;
    }

    /**
     * @param replyNote the replyNote to set
     */
    public void setReplyNote(String replyNote) {
        this.replyNote = replyNote;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @param statusText the statusText to set
     */
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    /**
     * @param holdType the holdType to set
     */
    public void setHoldType(String holdType) {
        this.holdType = holdType;
    }

    /**
     * @param itemTitle the itemTitle to set
     */
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    /**
     * @param expiredDate the expiredDate to set
     */
    public void setExpiredDate(String expiredDate) {
        this.expiredDate = expiredDate;
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

    /**
     * @param queuePosition the queuePosition to set
     */
    public void setQueuePosition(String queuePosition) {
        this.queuePosition = queuePosition;
    }

    /**
     * @param pickupLocation the pickupLocation to set
     */
    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    /**
     * @param pickupLocationCode the pickupLocationCode to set
     */
    public void setPickupLocationCode(String pickupLocationCode) {
        this.pickupLocationCode = pickupLocationCode;
    }

    /**
     * @param instName the instName to set
     */
    public void setInstName(String instName) {
        this.instName = instName;
    }

}
