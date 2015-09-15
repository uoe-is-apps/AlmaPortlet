package uk.ac.ed.portlets.almaportlet.portlet.test;

import static org.springframework.util.Assert.notNull;

import javax.portlet.PortletSession;

import org.springframework.test.web.portlet.server.PortletMvcResult;
import org.springframework.test.web.portlet.server.PortletResultMatcher;


public class SessionResultMatchers {
	
	protected SessionResultMatchers() {}
	
	
	
	public PortletResultMatcher exists(){
		
		return new PortletResultMatcher() {
			
			@Override
			public void match(PortletMvcResult result) throws Exception {
				
				notNull(result.getRequest().getPortletSession(false));
			}
		};
	}
	
	
	
	public PortletResultMatcher hasAttribute(final String name){
		
		return new PortletResultMatcher() {

			@Override
			public void match(PortletMvcResult result) throws Exception {
				
				notNull(result.getRequest().getPortletSession(false).getAttribute(name, PortletSession.APPLICATION_SCOPE), "Session attribute:" + name + " does not exist");
				
			}
			
		};
	}

}
