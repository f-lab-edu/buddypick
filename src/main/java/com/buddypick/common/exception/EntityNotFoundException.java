package com.buddypick.common.exception;

import com.buddypick.common.error.ApiException;
import com.buddypick.common.error.ErrorCode;

public class EntityNotFoundException extends ApiException {

	public EntityNotFoundException(ErrorCode e) {
		super(e.getMessage(), e);
	}

	public EntityNotFoundException(String message, ErrorCode e) {
		super(message, e);
	}

	public ErrorCode getErrorCode() {
		return getErrorCode();
	}
}
