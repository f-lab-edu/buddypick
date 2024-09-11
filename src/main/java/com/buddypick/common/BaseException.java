package com.buddypick.common;

import com.buddypick.error.ErrorCode;
import com.buddypick.error.ErrorResponse;
import com.buddypick.exception.EntityNotFoundException;
import com.buddypick.exception.UserNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class BaseException {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResponse illegalArgumentException(IllegalArgumentException e){
        //e.printStackTrace();
        log.error("[IllegalArgumentException] ex", e);
        return new ErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public ErrorResponse validationException(BindException e){
        log.error("[BindException] ex ", e);
        ErrorResponse response = new ErrorResponse(ErrorCode.INVALID_REQUEST);
        for(FieldError fieldError : e.getFieldErrors()){
            response.addValidation(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return response;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ErrorResponse entityNotFoundException(EntityNotFoundException e){
        log.error("[EntityNotFoundException] ex ", e);
        ErrorResponse response = new ErrorResponse(e.getErrorCode(), e.getMessage());
        return response;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse userNotFoundException(UserNotFoundException e){
        log.error("[UserNotFoundException] ex ", e);
        ErrorResponse response = new ErrorResponse(e.getErrorCode(), e.getMessage());
        //ErrorResponse response = new ErrorResponse(ErrorCode.NOT_EXIST_USER);
        return response;
    }

}
