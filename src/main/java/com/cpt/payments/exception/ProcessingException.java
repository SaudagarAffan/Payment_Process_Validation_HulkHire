package com.cpt.payments.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ProcessingException extends RuntimeException {
	
	private String errorCode;
	private String errorMessage;
	private HttpStatus httpStatus;
	
	public ProcessingException(String errorCode, String errorMessage, 
			HttpStatus httpStatus) {
		super(errorMessage);
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
	}

}
