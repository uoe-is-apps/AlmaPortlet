package uk.ac.ed.portlets.almaportlet.portlet;

import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.portlet.server.setup.PortletMockMvcBuilders.*;
import static org.springframework.test.web.portlet.server.result.PortletMockMvcResultMatchers.*;
import static uk.ac.ed.portlets.almaportlet.portlet.test.PortletMockMvcResultMatchersExtension.*;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.portlet.server.PortletMvcResult;

import uk.ac.ed.portlets.almaportlet.json.UserInfo;
import uk.ac.ed.portlets.almaportlet.portlet.test.CustomMockRenderRequestBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-test.xml"})
public class PortletIntegrationTest {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Test
	public void portletReturnsMainViewAndApplicationSessionContainsNonvalidatedUserInfo() throws Exception {

		Map<String,String> userInfoMap = new HashMap<String, String>();
		userInfoMap.put("librarynumber", "TestingA");
		userInfoMap.put("sn", "Testing");
		
		PortletMvcResult result = existingApplicationContext(applicationContext).build()
			.perform(new CustomMockRenderRequestBuilder().attribute(PortletRequest.USER_INFO,userInfoMap))
			.andExpect(view().name("main"))
			.andExpect(session().exists())
			.andExpect(session().hasAttribute(UserInfo.SESSION_ATTR))
			.andReturn();
				
		UserInfo userInfo = (UserInfo) result.getRequest().getPortletSession(false).getAttribute(UserInfo.SESSION_ATTR,PortletSession.APPLICATION_SCOPE);
		
		assertFalse(userInfo.isValidated());

	}
}
