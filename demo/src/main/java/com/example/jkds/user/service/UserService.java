package com.example.jkds.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.jkds.security.jwt.TokenProvider;
import com.example.jkds.security.jwt.UserDetailService;
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
	
	public String authentication(String userId, String userPassword) {
		String accessToken = null;
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userId, userPassword);
			Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
			accessToken = tokenProvider.createToken(authentication);
		} catch (Exception e) {
			accessToken = null;
		}
		return accessToken;
	}
}
