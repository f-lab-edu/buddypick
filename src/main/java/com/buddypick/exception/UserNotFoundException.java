package com.buddypick.exception;

import com.buddypick.error.ErrorCode;

public class UserNotFoundException extends RuntimeException {
    private final ErrorCode errorCode;

    public UserNotFoundException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public UserNotFoundException(String message) {
        super(message);
        this.errorCode = ErrorCode.NOT_EXIST_USER;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

}
