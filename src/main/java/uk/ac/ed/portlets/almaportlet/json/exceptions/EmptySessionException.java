package uk.ac.ed.portlets.almaportlet.json.exceptions;

/*
 * Exception thrown when session attributes are lost, or failed to be initialised
 */
public class EmptySessionException extends Exception {

	public EmptySessionException(String message){
		
		super(message);
	}
}
