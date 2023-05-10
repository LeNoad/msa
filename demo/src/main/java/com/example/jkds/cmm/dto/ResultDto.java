package com.example.jkds.cmm.dto;

import java.util.HashMap;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResultDto<T> {
	private int status;
	private String message;
	private HashMap<String, T> result = null;

	public ResultDto<T> makeResult(HttpStatus httpStatus, String message) {
		this.status = httpStatus.value();
		this.message = message;
		return this;
	}

	public ResultDto<T> makeResult(HttpStatus httpStatus, String message, T resultDto, String MapName) {
		this.status = httpStatus.value();
		this.message = message;
		HashMap<String, T> result = new HashMap<>();
		result.put(MapName, resultDto);
		this.result = result;
		return this;
	}
}