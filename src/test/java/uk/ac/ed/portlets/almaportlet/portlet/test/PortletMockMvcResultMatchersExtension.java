package uk.ac.ed.portlets.almaportlet.portlet.test;

public final class PortletMockMvcResultMatchersExtension {
	
	public static SessionResultMatchers session(){
		
		return new SessionResultMatchers();
	}

}
