package uk.ac.ed.portlets.almaportlet.json.exceptions;

/*
 * Exception thrown when user has an invalid library barcode or is blocked from accessing the library
 */
public class PatronLibraryAccessException extends Exception {
	
	public PatronLibraryAccessException(String message){
		
		super(message);
	}

}
