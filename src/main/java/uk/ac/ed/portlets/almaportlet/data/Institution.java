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

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.ac.ed.portlets.almaportlet.exceptions.PatronResponseException;


public class Institution {
    private String id;
    private String name;
    private Map<String, Action> actions = new HashMap<String, Action>();
    
    public void addAction(final String type, final Action action) {
        this.actions.put(type, action);
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the actions
     */
    public Map<String, Action> getActions() {
        return actions;
    }
    
    public Action parseAction(final Element element)
        throws PatronResponseException {
        final Action action = new Action();
        final NamedNodeMap attributes = element.getAttributes();
        
        action.setType(attributes.getNamedItem("type").getTextContent().trim());
        if (null != attributes.getNamedItem("href")) {
            action.setHref(attributes.getNamedItem("href").getTextContent().trim());
        }
        
        final NodeList institutionChildNodes = element.getChildNodes();
        
        for (int childIdx = 0; childIdx < institutionChildNodes.getLength(); childIdx++) {
            final Node childNode = institutionChildNodes.item(childIdx);
            
            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            
            if (childNode.getNodeName().equals("amount")) {
                try {
                    action.setAmount(Integer.parseInt(childNode.getTextContent().trim()));
                } catch(NumberFormatException e) {
                    throw new PatronResponseException("Expected integer value for action \"amount\", found \""
                        + childNode.getTextContent().trim() + "\" instead.");
                }
            } else if (childNode.getNodeName().equals("finesum")) {
                action.setFinesum(childNode.getTextContent().trim());
            }
        }
        
        return action;
    }
    
    public static Institution parseInstitution(final Element institutionElement)
            throws PatronResponseException {
        final Institution institution = new Institution();
        final NamedNodeMap attributes = institutionElement.getAttributes();
        final NodeList institutionChildNodes = institutionElement.getChildNodes();
        final Node rawId = attributes.getNamedItem("id");
        
        if (null == rawId) {
            throw new PatronResponseException("Institution must have an ID.");
        }
        
        institution.setId(rawId.getTextContent().trim());
        
        for (int childIdx = 0; childIdx < institutionChildNodes.getLength(); childIdx++) {
            final Node childNode = institutionChildNodes.item(childIdx);
            
            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            
            if (childNode.getNodeName().equals("instName")) {
                institution.setName(childNode.getTextContent().trim());
            } else if (childNode.getNodeName().equals("action")) {
                final Action action = institution.parseAction((Element)childNode);
                
                institution.addAction(action.getType(), action);
            }
        }
        
        return institution;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    
    public class Action {
        private String type;
        private String href;
        private Integer amount;
        private String finesum;

        /**
         * @return the type
         */
        public String getType() {
            return type;
        }

        /**
         * @return the href
         */
        public String getHref() {
            return href;
        }

        /**
         * @return the amount
         */
        public Integer getAmount() {
            return amount;
        }

        /**
         * @return the fine summary, for example "GBP 12.90".
         */
        public String getFinesum() {
            return finesum;
        }

        /**
         * @param type the type to set
         */
        public void setType(String type) {
            this.type = type;
        }

        /**
         * @param href the href to set
         */
        public void setHref(String href) {
            this.href = href;
        }

        /**
         * @param amount the amount to set
         */
        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        /**
         * @param finesum the finesum to set
         */
        public void setFinesum(final String finesum) {
            this.finesum = finesum;
        }
    }

}
