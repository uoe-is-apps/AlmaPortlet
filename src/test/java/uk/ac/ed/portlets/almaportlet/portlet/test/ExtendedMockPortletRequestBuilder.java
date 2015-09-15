package uk.ac.ed.portlets.almaportlet.portlet.test;

/*
 * adds simple request attribute support to MockPortletRequestBuilder
 */
import java.util.HashMap;
import java.util.Map;

import org.springframework.mock.web.portlet.MockPortletRequest;
import org.springframework.test.web.portlet.server.request.MockPortletRequestBuilder;
import org.springframework.util.Assert;

public class ExtendedMockPortletRequestBuilder extends MockPortletRequestBuilder {
	
	private final Map<String, Object> attributes = new HashMap<String, Object>();
	
	protected void addAttribute(String name, Object obj) {
		Assert.hasLength(name, "'name' must not be empty");
		Assert.notNull(obj, "'obj' is required");
		attributes.put(name, obj);
	}
	
	protected void setAttributes(MockPortletRequest request) {
		for (String name : this.attributes.keySet()) {
			Object params = this.attributes.get(name);
			request.setAttribute(name, params);
		}
	}
}
