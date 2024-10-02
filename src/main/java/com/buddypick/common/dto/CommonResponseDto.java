package com.buddypick.common.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor
public class CommonResponseDto<T> {

    private HttpStatus status;
    private String message;
    private T data;

    public CommonResponseDto(HttpStatus httpStatus, String message, T data) {
        this.status = httpStatus;
        this.message = message;
        this.data = data;
    }

    public CommonResponseDto(HttpStatus httpStatus, String message) {
        this.status = httpStatus;
        this.message = message;
    }

    public CommonResponseDto(HttpStatus httpStatus, T data) {
        this.status = httpStatus;
        this.data = data;
    }
}
