package com.buddypick.common.error;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

	private final HttpStatus status;
	private final String message;
	private Map<String, String> data = new HashMap<>();

	public void addValidation(String fieldName, String message) {
		this.data.put(fieldName, message);
	}

	public ErrorResponse(ErrorCode errorCode) {
		this(errorCode.getHttpStatus(), errorCode.getMessage());
	}

	public ErrorResponse(ErrorCode errorCode, String message) {
		this(errorCode.getHttpStatus(), message);
	}
}
