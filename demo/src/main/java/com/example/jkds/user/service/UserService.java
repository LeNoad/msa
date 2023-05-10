package com.example.jkds.user.service;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.example.jkds.cmm.dto.JwtTokenDto;
import com.example.jkds.security.jwt.TokenProvider;
import com.example.jkds.user.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    
	public UserService(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder,
			UserRepository userRepository) {
		this.tokenProvider = tokenProvider;
		this.authenticationManagerBuilder = authenticationManagerBuilder;
	}
	
	public JwtTokenDto authentication(String userId, String userPassword) {
		JwtTokenDto jwtTokenDto = null;
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, userPassword);
			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			jwtTokenDto = tokenProvider.createToken(authentication);
		} catch (Exception e) {
			jwtTokenDto = null;
		}
		return jwtTokenDto;
	}
}
