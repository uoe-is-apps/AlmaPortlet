package uk.ac.ed.portlets.almaportlet.portlet.test;

import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.test.web.portlet.server.request.RenderRequestBuilder;

public class CustomMockRenderRequestBuilder extends ExtendedMockPortletRequestBuilder implements RenderRequestBuilder {
	
	public CustomMockRenderRequestBuilder param(String name, String... values) {
		
		addParameter(name, values);
		return this;
	}
	
	public CustomMockRenderRequestBuilder attribute(String name, Object obj) {
		
		addAttribute(name, obj);
		return this;
	}
	
	
	public MockRenderRequest buildRequest() {
		
		MockRenderRequest request = new MockRenderRequest();
		
		setParameters(request);
		setAttributes(request);
		
		return request;
	}

}
