package com.buddypick.common.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {

	private final HttpStatus status;
	private final String message;

	public ErrorResponse(ErrorCode errorCode) {
		this(errorCode.getStatus(), errorCode.getMessage());
	}

	public ErrorResponse(ErrorCode errorCode, String message) {
		this(errorCode.getStatus(), message);
	}
}
