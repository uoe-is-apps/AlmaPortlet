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

import org.apache.xerces.dom.CoreDocumentImpl;
import org.apache.xerces.dom.ElementImpl;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.w3c.dom.Element;

import uk.ac.ed.portlets.almaportlet.exceptions.PatronResponseException;


public class XmlUtilTest {    
    @Test
    public void getUniqueChildTestShouldReturnElement() throws PatronResponseException {
        final CoreDocumentImpl document = new CoreDocumentImpl();
        final Element outerElement = new ElementImpl(document, "outer");
        final Element singleChild = new ElementImpl(document, "child");
        final Element otherChild = new ElementImpl(document, "other_child");
        
        document.appendChild(outerElement);
        outerElement.appendChild(singleChild);
        outerElement.appendChild(otherChild);
        
        final Element result = XmlUtil.getUniqueChild(outerElement, "child");
        
        assertEquals(singleChild, result);
    }
    
    @Test(expected=PatronResponseException.class)
    public void getUniqueChildTestShouldErrorOnTwoChildren() throws PatronResponseException {
        final CoreDocumentImpl document = new CoreDocumentImpl();
        final Element outerElement = new ElementImpl(document, "outer");
        final Element firstChild = new ElementImpl(document, "child");
        final Element secondChild = new ElementImpl(document, "child");
        
        document.appendChild(outerElement);
        outerElement.appendChild(firstChild);
        outerElement.appendChild(secondChild);
        
        XmlUtil.getUniqueChild(outerElement, "child");
    }
    
    @Test(expected=PatronResponseException.class)
    public void getUniqueChildTestShouldErrorOnNoMatch() throws PatronResponseException {
        final CoreDocumentImpl document = new CoreDocumentImpl();
        final Element outerElement = new ElementImpl(document, "outer");
        final Element firstChild = new ElementImpl(document, "childA");
        final Element secondChild = new ElementImpl(document, "childB");
        
        document.appendChild(outerElement);
        outerElement.appendChild(firstChild);
        outerElement.appendChild(secondChild);
        
        XmlUtil.getUniqueChild(outerElement, "child");
    }
}
