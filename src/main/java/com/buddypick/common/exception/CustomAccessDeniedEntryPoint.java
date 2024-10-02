package com.buddypick.common.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import com.buddypick.common.error.ErrorResponse;

import java.io.IOException;

@Slf4j
public class CustomAccessDeniedEntryPoint implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        ErrorResponse responseDto = new ErrorResponse(HttpStatus.FORBIDDEN, "허가되지 않은 접근입니다.");
        String responseBody = mapper.writeValueAsString(responseDto);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(403);
        response.getWriter().println(responseBody);

        log.info("권한없는 사용자 접근(FORBIDDEN)", e.getMessage());
    }
}
