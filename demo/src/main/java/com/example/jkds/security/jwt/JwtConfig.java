package com.example.jkds.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
	@Value("${jwt.auth.secret}")
	private String accessTokenSecret;
	@Value("${jwt.auth.access-token-validity-in-seconds}")
	private Long accessTokenValidityInSeconds;
	
	@Bean(name = "TokenProvider")
	public TokenProvider tokenProvider() {
		return new TokenProvider(accessTokenSecret, accessTokenValidityInSeconds);
	}
}
