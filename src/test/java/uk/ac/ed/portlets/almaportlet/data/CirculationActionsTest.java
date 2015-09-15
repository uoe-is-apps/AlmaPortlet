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
import org.w3c.dom.Element;

import org.junit.Test;
import org.junit.Assert;
import uk.ac.ed.portlets.almaportlet.exceptions.PatronResponseException;


public class CirculationActionsTest {
    /**
     * Verifies that institution parsing works as expected. Also tests institution
     * action parsing as a side-effect.
     * 
     * @throws PatronResponseException 
     */
    @Test
    public void parseCirculationActionsTest() throws PatronResponseException {
        final CoreDocumentImpl document = new CoreDocumentImpl();
        final Element circulationActionsElement = new ElementImpl(document, "circulationActions");
        final Element institutionElement = InstitutionTest.buildTestInstitution(document);
        final Element totalElement = buildTestTotals(document);
        
        circulationActionsElement.appendChild(institutionElement);
        circulationActionsElement.appendChild(totalElement);
        document.appendChild(circulationActionsElement);
        
        final CirculationActions circulationActions = CirculationActions.parseCirculationActions(circulationActionsElement);
        
        Assert.assertEquals("Edinburgh University Library", circulationActions.getInstitution().getName());
        Assert.assertEquals("1", circulationActions.getTotalLoans());
        Assert.assertEquals("GBP 12.90", circulationActions.getTotalFines());
        Assert.assertEquals("4", circulationActions.getTotalRequests());
    }

    private Element buildTestTotals(CoreDocumentImpl document) {
        final Element outerTotalElement = new ElementImpl(document, "total");
        final Element totalLoansElement = new ElementImpl(document, "total");
        final Element totalFinesElement = new ElementImpl(document, "total");
        final Element totalRequestsElement = new ElementImpl(document, "total");
        
        totalLoansElement.setAttribute("type", "Loans");
        totalLoansElement.setTextContent("1");
        totalFinesElement.setAttribute("type", "Fines");
        totalFinesElement.setTextContent("GBP 12.90");
        totalRequestsElement.setAttribute("type", "Requests");
        totalRequestsElement.setTextContent("4");
        
        outerTotalElement.appendChild(totalLoansElement);
        outerTotalElement.appendChild(totalFinesElement);
        outerTotalElement.appendChild(totalRequestsElement);
        
        return outerTotalElement;
    }

}
