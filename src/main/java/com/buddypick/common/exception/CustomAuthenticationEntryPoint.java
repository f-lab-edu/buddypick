package com.buddypick.common.exception;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import com.buddypick.common.error.ErrorResponse;

import java.io.IOException;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse responseDto = new ErrorResponse(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.");
        String responseBody = mapper.writeValueAsString(responseDto);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(401);
        response.getWriter().println(responseBody);

        log.info("인증 되지 않은 사용자 접근 (UNAUTHORIZED)", e.getMessage());
    }
}
