package uk.ac.ed.portlets.almaportlet.json.exceptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import uk.ac.ed.portlets.almaportlet.json.ErrorInfo;

@ControllerAdvice
public class GlobalServletExceptionHandler {
	
	@Value("${patronError}")
	private String patronError;
	
	@Value("${systemError}")
	private String systemError;
	
	@Value("${accessError}")
	private String accessError;
	
	@Value("${basicLoginUrl}")
	private String basicLoginUrl;
	
	@ExceptionHandler(EmptySessionException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ErrorInfo handleSystemError(EmptySessionException e){
		
		return new ErrorInfo(systemError, e.getMessage());
	}
	
	@ExceptionHandler(PatronDetailsRetrievalException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorInfo handlePatronError(PatronDetailsRetrievalException e){
		
		return new ErrorInfo(patronError, e.getMessage());
	}
	
	@ExceptionHandler(PatronLibraryAccessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorInfo handleAccessError(PatronLibraryAccessException e){
		
		return new ErrorInfo(accessError, e.getMessage(), basicLoginUrl);
	}
	
	
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
    public ErrorInfo handleWebServiceError(Exception e){
		
		return new ErrorInfo(systemError, e.getMessage());
	}

}
