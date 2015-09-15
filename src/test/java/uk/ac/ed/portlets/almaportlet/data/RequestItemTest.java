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


public class RequestItemTest {
    private final String EXAMPLE_REQUEST_ITEM = "<requestItem><itemId>873528</itemId><holdRecallId>5093</holdRecallId><replyNote>Test for Gordon Andrew</replyNote><status>5</status><statusText>Not Filled 2012-11-13</statusText><holdType>C</holdType><itemTitle>VI International Conference on Goats : 6-11 May 1996 Beijing, China.  v.1</itemTitle><expiredDate>2012-11-13</expiredDate><dbKey>EDDB20020903165307</dbKey><dbName>Edinburgh University Library</dbName><queuePosition/><pickupLocation>Main Library Service Desk</pickupLocation><pickupLocationCode>MAIN</pickupLocationCode><instName>Edinburgh University Library</instName></requestItem>";
    
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
        
        final JAXBContext jc = JAXBContext.newInstance(RequestItem.class);
        final Unmarshaller unmarshaller = jc.createUnmarshaller();
        final RequestItem item = (RequestItem)unmarshaller.unmarshal(document);
        
        Assert.assertEquals("873528", item.getItemId());
        Assert.assertEquals("5093", item.getHoldRecallId());
        Assert.assertEquals("VI International Conference on Goats : 6-11 May 1996 Beijing, China.  v.1", item.getItemTitle());
    }
}
