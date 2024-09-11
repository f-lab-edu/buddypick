package com.buddypick.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),
    NOT_FOUND_ENTITY(HttpStatus.NOT_FOUND, "해당 데이터를 찾을 수 없습니다."),
    NOT_EXIST_USER(HttpStatus.NOT_FOUND, "존재하지 않는 회원입니다."),
    DUPLICATE_USER(HttpStatus.BAD_REQUEST, "이미 가입된 회원입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}

