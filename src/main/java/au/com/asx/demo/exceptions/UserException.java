package au.com.asx.demo.exceptions;

import org.springframework.http.HttpStatus;

public class UserException {

	private static final long serialVersionUID = 1L;
	private String message;
	private HttpStatus status;
	
	public UserException(String message, HttpStatus status) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public HttpStatus getStatus() {
		return status;
	}
	
	
}
