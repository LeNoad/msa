package com.example.jkds.cmm.dto;

import java.util.Date;

import lombok.Data;

@Data
public class JwtTokenDto {
	private String accessToken;
	private String refreshToken;
	private Date expiresIn;
	
	public JwtTokenDto(String accessToken, String refreshToken, Date expiresIn) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
	}
}
