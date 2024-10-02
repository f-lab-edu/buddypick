package com.buddypick.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.Map;

public class CommonResponse<T> {

	public static void successResponse(HttpServletResponse response, String message, Map data) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		CommonResponseDto<?> responseDto = new CommonResponseDto<>(HttpStatus.OK, message, data);
		String responseBody = mapper.writeValueAsString(responseDto);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setStatus(200);
		response.getWriter().println(responseBody);
	}

	public static void successResponse(HttpServletResponse response, Map data) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		CommonResponseDto<?> responseDto = new CommonResponseDto<>(HttpStatus.OK, data);
		String responseBody = mapper.writeValueAsString(responseDto);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setStatus(200);
		response.getWriter().println(responseBody);
	}

	public static void errorResponse(HttpServletResponse response, String message) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		CommonResponseDto<?> responseDto = new CommonResponseDto<>(HttpStatus.UNAUTHORIZED, message);
		String responseBody = mapper.writeValueAsString(responseDto);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setStatus(401);
		response.getWriter().println(responseBody);
	}
}