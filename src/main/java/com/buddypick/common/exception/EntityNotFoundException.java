package com.buddypick.common.exception;

import com.buddypick.common.error.ApiException;
import com.buddypick.common.error.ErrorCode;

public class EntityNotFoundException extends ApiException {

	public EntityNotFoundException(String message) {
		super(message);
	}

	@Override
	public String getErrorCode() {
		return "ENTITY_NOT_FOUND";
	}
}
