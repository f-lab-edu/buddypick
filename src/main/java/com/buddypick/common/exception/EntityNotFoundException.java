package com.buddypick.common.exception;

import com.buddypick.common.error.ErrorCode;

public class EntityNotFoundException extends RuntimeException {
	private final ErrorCode errorCode;

	public EntityNotFoundException(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	public EntityNotFoundException(String message) {
		super(message);
		this.errorCode = ErrorCode.NOT_FOUND_ENTITY;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
}
