package uk.ac.ed.portlets.almaportlet.json.exceptions;

/*
 * Exception thrown when user has no surname or library number, or when patron ID retrieved is null
 */

public class PatronDetailsRetrievalException extends Exception {

	
	public PatronDetailsRetrievalException(String message){
		
		super(message);
	}
	
}
