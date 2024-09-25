package com.buddypick.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.NoArgsConstructor;

@JsonInclude(Include.NON_NULL)
@NoArgsConstructor
public class CommonResponse<T> {
	private String code;
	private String message;
	private T data;

	public CommonResponse(String code, String message, T data) {
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public CommonResponse(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
