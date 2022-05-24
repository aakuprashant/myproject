package com.product.error.handler;

import org.springframework.http.HttpStatus;

public class ErrorHandler {
	
	
	private HttpStatus httpStatus;
	private String timeStamp;
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public HttpStatus getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public ErrorHandler(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}
	public ErrorHandler() {}
	

}
