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

import java.util.HashMap;
import java.util.Map;

import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.ac.ed.portlets.almaportlet.exceptions.PatronResponseException;
import uk.ac.ed.portlets.almaportlet.util.XmlUtil;


public class CirculationActions {
    private Institution institution;
    private final Map<String, String> totals = new HashMap<String, String>();
    
    public void addTotal(final String type, final String value) {
        this.totals.put(type, value);
    }

    /**
     * @return the institution
     */
    public Institution getInstitution() {
        return institution;
    }

    /**
     * @return the totals
     */
    public Map<String, String> getTotals() {
        return totals;
    }

    /**
     * @param institution the institution to set
     */
    public void setInstitution(Institution institution) {
        this.institution = institution;
    }

    public String getTotalFines() {
        return this.getTotals().get("Fines");
    }

    public String getTotalLoans() {
        return this.getTotals().get("Loans");
    }

    public String getTotalRequests() {
        return this.getTotals().get("Requests");
    }
    
    public static CirculationActions parseCirculationActions(final Element circulationActionsElement)
            throws PatronResponseException, DOMException {
        final NodeList totalNodes = XmlUtil.getUniqueChild(circulationActionsElement, "total").getChildNodes();
        final CirculationActions circulationActions = new CirculationActions();
        final Institution institution = Institution.parseInstitution(XmlUtil.getUniqueChild(circulationActionsElement, "institution"));
        
        circulationActions.setInstitution(institution);
        
        for (int totalIdx = 0; totalIdx < totalNodes.getLength(); totalIdx++) {
            final Node totalNode = totalNodes.item(totalIdx);
            final NamedNodeMap attributes = totalNode.getAttributes();
            final String type = attributes.getNamedItem("type").getTextContent();
            final String value = totalNode.getTextContent();
            
            circulationActions.addTotal(type, value);
        }
        
        return circulationActions;
    }

    public boolean hasFines() {
        return null != this.getTotalFines()
            && !this.getTotalFines().equals("0");
    }

    public boolean hasLoans() {
        return null != this.getTotalLoans()
            && !this.getTotalLoans().equals("0");
    }

    public boolean hasRequests() {
        return null != this.getTotalRequests()
            && !this.getTotalRequests().equals("0");
    }
}
