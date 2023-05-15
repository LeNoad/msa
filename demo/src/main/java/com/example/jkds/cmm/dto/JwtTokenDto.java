package com.example.jkds.cmm.dto;

import java.util.Date;

import com.example.jkds.user.entity.User;

import lombok.Data;

@Data
public class JwtTokenDto {
	private String accessToken;
	private String refreshToken;
	private User user;
	private Date expiresIn;
	
	public JwtTokenDto(String accessToken, String refreshToken, Date expiresIn, User user) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
		this.user = user; 
	}
}
