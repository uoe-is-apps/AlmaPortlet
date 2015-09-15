package uk.ac.ed.portlets.almaportlet.servlet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import uk.ac.ed.portlets.almaportlet.json.UserInfo;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:servletContext-test.xml"})
@WebAppConfiguration
public class ServletIntegrationTest {

	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Before
	public void setUp(){
		
		this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void whenUserInfoNotInSessionThenEmptySessionExceptionIsThrown() throws Exception{

		mockMvc.perform(get("/summary")
				.accept(MediaType.APPLICATION_JSON)
				.with(new RequestPostProcessor() {
					
					@Override
					public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
						
						request.getSession(true);
						return request;
					}
				}))
				.andExpect(status().isInternalServerError())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON));
	}

	@Test
	public void whenUserInfoHasInvalidBarcodeThenPatronLibraryAccessExceptionIsThrown() throws Exception {

		final UserInfo userInfo = new UserInfo("BadUUN","BadBarcode","BadSurname");

			mockMvc.perform(get("/summary")
				.accept(MediaType.APPLICATION_JSON)
				.with(new RequestPostProcessor() {
					
					@Override
					public MockHttpServletRequest postProcessRequest(MockHttpServletRequest request) {
						
						MockHttpSession mockSession = (MockHttpSession) request.getSession(true);
						mockSession.setAttribute(UserInfo.SESSION_ATTR, userInfo);
						request.setSession(mockSession);
						
						return request;
					}
				}))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.*", hasSize(9)))
				.andExpect(jsonPath("$.message", nullValue()))
				.andExpect(jsonPath("$.payload", nullValue()))
				.andReturn();
	}
	
}
