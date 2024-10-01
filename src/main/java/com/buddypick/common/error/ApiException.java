package com.buddypick.common.error;

import org.springframework.http.HttpStatus;

abstract public class ApiException extends RuntimeException {

	private  HttpStatus httpStatus;
	private  String errorMessage;

	public ApiException(HttpStatus httpStatus, String errorMessage){
		super(errorMessage);
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    };

	public ApiException(String message){
		super(message);
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	abstract public String getErrorCode();
}
