package com.buddypick.user.exception;

import com.buddypick.common.error.ApiException;

public class UserNotFoundException extends ApiException {

	public UserNotFoundException(String message) {
		super(message);
	}

	@Override
	public String getErrorCode() {
		return "USER_NOT_FOUND";
	}

}
