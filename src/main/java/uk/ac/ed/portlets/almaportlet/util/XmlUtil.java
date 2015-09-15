/*
 * Copyright 2014 Jasig.
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

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import uk.ac.ed.portlets.almaportlet.exceptions.PatronResponseException;


public class XmlUtil {
    private             XmlUtil() {
    }
    
    /**
     * Helper function to find a unique child node within the given document.
     * Throws PatronResponseException if there is not exactly one matching child.
     * 
     * @param node the node to find a child of.
     * @param tagName the name of the XML tag to find.
     * @return a node.
     * @throws PatronResponseException if there is not exactly one matching child.
     */
    public static Element getUniqueChild(Node node, String tagName)
            throws PatronResponseException {
        final NodeList candidates = node.getChildNodes();
        Node match = null;
        
        for (int nodeIdx = 0; nodeIdx < candidates.getLength(); nodeIdx++) {
            final Node childNode = candidates.item(nodeIdx);
            
            if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                continue;
            }
            
            if (childNode.getNodeName().equals(tagName)) {
                if (null != match) {
                    throw new PatronResponseException("More than one \""
                        + tagName + "\" node found in returned document, expected exactly one.");
                }
                match = childNode;
            }
        }
        
        if (null == match) {
            throw new PatronResponseException("Zero \""
                + tagName + "\" nodes found in returned document, expected exactly one.");
        }
        
        return (Element)match;
    }
}
