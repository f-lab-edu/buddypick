package com.buddypick.common.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

	private  HttpStatus status;
	private  String message;
	private  String errorCode;

	public ErrorResponse(ApiException e){
		this.status = e.getHttpStatus();
		this.message = e.getMessage();
		this.errorCode = e.getErrorCode();
	}

	public ErrorResponse(HttpStatus status, String message) {
        this.status = status;
		this.message = message;
	}

}
