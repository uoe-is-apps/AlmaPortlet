package uk.ac.ed.portlets.almaportlet.json;

public class ErrorInfo {

	private String message; /* user friendly message */
	
	private String error; /* error message */
	
	private String payload; /* additional info */
	
	
	public ErrorInfo() {}
	
	public ErrorInfo(String message){
		
		this.message = message;
	}
	
	public ErrorInfo(String message, String error){
		
		this.message = message;
		this.error = error;
	}

	public ErrorInfo(String message, String error, String payload){
		
		this.message = message;
		this.error = error;
		this.payload = payload;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
}
