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


public class FineTest {
    private final String EXAMPLE_FINE_ITEM = "<fine href=\"http://127.0.0.1:7014/vxws/patron/123456/circulationActions/debt/fines/123456?patron_homedb=1234\"><fineId>1517083</fineId><fineDate>2013-10-14</fineDate><itemTitle>One flew over the cuckoo's nest / a novel by Ken Kesey. </itemTitle><fineType>Overdue</fineType><amount>GBP 5.00</amount><dbKey>EDDB20020903165307</dbKey><dbName>Edinburgh University Library</dbName></fine>";
    
    @Test
    public void unmarshallingTest()
        throws ParserConfigurationException, SAXException, IOException, JAXBException {
        final DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        final DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        final ByteArrayInputStream in = new ByteArrayInputStream(EXAMPLE_FINE_ITEM.getBytes());
        final Document document;
        
        try {
            document = dBuilder.parse(in);
        } finally {
            in.close();
        }
        
        final JAXBContext jc = JAXBContext.newInstance(Fine.class);
        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        final Fine item = (Fine)unmarshaller.unmarshal(document);
        
        Assert.assertEquals("1517083", item.getFineId());
        Assert.assertEquals("One flew over the cuckoo's nest / a novel by Ken Kesey. ", item.getItemTitle());
    }
}
