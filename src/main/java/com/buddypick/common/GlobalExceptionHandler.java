package com.buddypick.common;

import com.buddypick.common.error.ApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.buddypick.common.error.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ApiException.class)
	public ErrorResponse handleApiException(ApiException e){
		log.error("[handleApiException] ex", e);
		return new ErrorResponse(e);
	}

	@ExceptionHandler(Exception.class)
	public ErrorResponse handleException(Exception e){
		log.error("[handleException] ex", e);
		return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	}


	/*
		@ResponseStatus(HttpStatus.BAD_REQUEST)
		@ExceptionHandler(BindException.class)
		public ErrorResponse validationException(BindException e) {
			log.error("[BindException] ex ", e);
			ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_REQUEST);
			for (FieldError fieldError : e.getFieldErrors()) {
				response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
			}
			return response;
		}
	*/

}
