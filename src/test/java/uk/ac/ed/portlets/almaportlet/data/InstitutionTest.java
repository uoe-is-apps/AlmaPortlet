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

package uk.ac.ed.portlets.almaportlet.data;

import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.w3c.dom.Element;

import uk.ac.ed.portlets.almaportlet.exceptions.PatronResponseException;

public class InstitutionTest {
    /**
     * Verifies that institution parsing works as expected. Also tests institution
     * action parsing as a side-effect.
     * 
     * @throws PatronResponseException 
     */
    @Test
    public void parseInstitutionTest() throws PatronResponseException {
        final CoreDocumentImpl document = new CoreDocumentImpl();
        final Element institutionNode = this.buildTestInstitution(document);
        
        document.appendChild(institutionNode);
        
        final Institution institution = Institution.parseInstitution(institutionNode);
        
        assertEquals(institution.getId(), "LOCAL");
        assertEquals(institution.getName(), "Edinburgh University Library");
        assertEquals(3, institution.getActions().size());
        
        final Institution.Action finesumAction = institution.getActions().get("Debt");
        
        assertNotNull(finesumAction);
        assertEquals("http://127.0.0.1:7014/vxws/patron/123456/circulationActions/debt?institution=LOCAL&patron_homedb=1234",
            finesumAction.getHref());
        assertEquals("GBP 12.90",
            finesumAction.getFinesum());
    }
    
    /**
     * Verifies that institution parsing works as expected. Also tests institution
     * action parsing as a side-effect.
     * 
     * @throws PatronResponseException 
     */
    @Test(expected=PatronResponseException.class)
    public void parseInstitutionTestShouldFail() throws PatronResponseException {
        final CoreDocumentImpl document = new CoreDocumentImpl();
        final Element institutionNode = this.buildTestInstitution(document);
        
        document.appendChild(institutionNode);
        
        // Break the data
        institutionNode.removeAttribute("id");
        
        Institution.parseInstitution(institutionNode);
    }
    
    /**
     * Verifies that institution parsing works as expected. Also tests institution
     * action parsing as a side-effect.
     * 
     * @throws PatronResponseException 
     */
    @Test
    public void parseInstitutionActionTest() throws PatronResponseException {
        final Institution institution = new Institution();
        final CoreDocumentImpl document = new CoreDocumentImpl();
        final Element institutionActionNode = this.buildAmountAction(document, "loans",
            "http://127.0.0.1:7014/vxws/patron/123456/circulationActions/loans?institution=LOCAL&patron_homedb=1234",
            1);
        
        document.appendChild(institutionActionNode);
        
        final Institution.Action action = institution.parseAction(institutionActionNode);
        
        assertEquals("http://127.0.0.1:7014/vxws/patron/123456/circulationActions/loans?institution=LOCAL&patron_homedb=1234",
            action.getHref());
        assertEquals("loans", action.getType());
        assertEquals(new Integer(1), action.getAmount());
    }
    
    static Element buildTestInstitution(final CoreDocumentImpl document) {
        /* <institution id="LOCAL">
                <instName>Edinburgh University Library</instName>
                <action type="loans" href="http://127.0.0.1:7014/vxws/patron/252470/circulationActions/loans?institution=LOCAL&patron_homedb=1@EDDB20020903165307">
                        <amount>1</amount>
                </action>
                <action type="Debt" href="http://127.0.0.1:7014/vxws/patron/252470/circulationActions/debt?institution=LOCAL&patron_homedb=1@EDDB20020903165307">
                        <finesum>GBP 12.90</finesum>
                </action>
                <action type="requests" href="http://127.0.0.1:7014/vxws/patron/252470/circulationActions/requests?institution=LOCAL&patron_homedb=1@EDDB20020903165307">
                        <amount>4</amount>
                </action>
        </institution> */
        
        final String id = "LOCAL";
        final String name = "Edinburgh University Library";
        final Element institution = new ElementImpl(document, "institution");
        final Element instName = new ElementImpl(document, "instName");
        
        institution.setAttribute("id", id);
        instName.setTextContent(name);
        
        institution.appendChild(instName);
        institution.appendChild(buildAmountAction(document, "loans",
                "http://127.0.0.1:7014/vxws/patron/123456/circulationActions/loans?institution=LOCAL&patron_homedb=1234",
                1));
        institution.appendChild(buildFineAction(document, "Debt",
                "http://127.0.0.1:7014/vxws/patron/123456/circulationActions/debt?institution=LOCAL&patron_homedb=1234",
                "GBP 12.90"));
        institution.appendChild(buildAmountAction(document, "requests",
                "http://127.0.0.1:7014/vxws/patron/123456/circulationActions/requests?institution=LOCAL&patron_homedb=1234",
                4));
        
        return institution;
    }

    /**
     * Constructs an empty &lt;action&gt; node, ready for an amount or finesum
     * element to be inserted into it.
     * 
     * @param document the document the node will be added to.
     * @param type the type of action.
     * @param href the href attribute for the action.
     * @return an action node.
     */
    public static Element buildActionNode(final CoreDocumentImpl document,
            final String type, final String href) {
        final ElementImpl actionNode = new ElementImpl(document, "action");
        
        actionNode.setAttribute("type", type);
        actionNode.setAttribute("href", href);
        
        return actionNode;
    }

    public static Element buildAmountAction(final CoreDocumentImpl document,
            final String type, final String href, final int amount) {
        final Element actionNode = buildActionNode(document, type, href);
        final ElementImpl amountNode = new ElementImpl(document, "amount");
        
        amountNode.setTextContent(Integer.toString(amount));
        
        actionNode.appendChild(amountNode);
        
        return actionNode;
    }

    public static Element buildFineAction(final CoreDocumentImpl document,
            final String type, final String href, final String finesum) {
        final Element actionNode = buildActionNode(document, type, href);
        final ElementImpl finesumNode = new ElementImpl(document, "finesum");
        
        finesumNode.setTextContent(finesum);
        
        actionNode.appendChild(finesumNode);
        
        return actionNode;
    }
}
