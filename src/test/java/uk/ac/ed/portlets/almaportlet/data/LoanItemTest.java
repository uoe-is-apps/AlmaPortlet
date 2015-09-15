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

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;


public class LoanItemTest {
    private final String EXAMPLE_REQUEST_ITEM = "<loan canRenew=\"N\" href=\"http://127.0.0.1:7014/vxws/patron/1234567/circulationActions/loans/1234|1234567?patron_homedb=1234\">"
            + "<itemId>1884267</itemId><itemBarcode>30150024697135</itemBarcode>"
            + "<dueDate>2014-10-13 23:59</dueDate>"
            + "<origDueDate>2012-05-30 23:59</origDueDate><todaysDate>2014-09-09 15:04</todaysDate><title>Bee thousand / Marc Woodworth.</title><author>Woodworth, Marc. </author><location>Main Library  (STANDARD LOAN) - 2nd floor</location><locationCode>MAIN-GEN2</locationCode><callNumber>ML421.G853 Woo.</callNumber><statusCode>3</statusCode><statusText>Renewed (Requests: 0)</statusText><itemtype>GENERAL</itemtype><dbKey>1234</dbKey><dbName>Edinburgh University Library</dbName></loan>";
    
    @Test
    public void unmarshallingTest()
        throws ParserConfigurationException, SAXException, IOException, JAXBException {
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        final ByteArrayInputStream in = new ByteArrayInputStream(EXAMPLE_REQUEST_ITEM.getBytes());
        final Document document;
        
        try {
            document = dBuilder.parse(in);
        } finally {
            in.close();
        }
        
        final JAXBContext jc = JAXBContext.newInstance(LoanItem.class);
        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        final LoanItem item = (LoanItem)unmarshaller.unmarshal(document);
        
        Assert.assertEquals("1884267", item.getItemId());
        Assert.assertEquals("N", item.getCanRenew());
        Assert.assertEquals("http://127.0.0.1:7014/vxws/patron/1234567/circulationActions/loans/1234|1234567?patron_homedb=1234", item.getHref());
        Assert.assertEquals("Bee thousand / Marc Woodworth.", item.getTitle());
        Assert.assertEquals("Woodworth, Marc. ", item.getAuthor());
    }
}
