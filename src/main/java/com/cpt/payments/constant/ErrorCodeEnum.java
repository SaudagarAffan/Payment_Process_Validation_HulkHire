package com.cpt.payments.constant;

public enum ErrorCodeEnum {
	
	// create errorCode & errorMessage files as part of this enum errroCode "100001", ""Amount cannot be negative"
	GENERIC_ERROR("20000", "Unable to process request, please try again later"),
	TEMP_ERROR("20001", "This is temporary error, please try again later");
	
	
	private String errorCode;
	private String errorMessage;
	
	ErrorCodeEnum(String errorCode, String errorMessage) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
	}
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	

}
