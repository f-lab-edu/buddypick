package com.buddypick.common.error;

public class ApiException extends RuntimeException {
	private ErrorCode code;

	public ApiException(ErrorCode e) {
		super(e.getMessage());
		this.code = e;
	}

	public ApiException(String message, ErrorCode e) {
		super(message);
		this.code = e;
	}

	public ErrorCode getErrorCode() {
		return code;
	}
}
