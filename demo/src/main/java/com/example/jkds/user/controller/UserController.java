package com.example.jkds.user.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.jkds.cmm.dto.JwtTokenDto;
import com.example.jkds.cmm.dto.ResultDto;
import com.example.jkds.security.jwt.TokenProvider;
import com.example.jkds.user.dto.LoginDto;
import com.example.jkds.user.entity.User;
import com.example.jkds.user.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenProvider tokenProvider;
	
	@Value("${jwt.auth.header}")
	private String AUTHORIZATION_HEADER;
	
	@RequestMapping("/api/authorize")
	public ResultDto<?> authorization(@RequestBody LoginDto loginDto) {
		JwtTokenDto jwtTokenDto = userService.authentication(loginDto.getUserId(), loginDto.getUserPassword());
        if(jwtTokenDto != null) {
        	return new ResultDto().makeResult(HttpStatus.OK, "로그인 성공", jwtTokenDto, "jwtTokenDto");
        } else {
        	return new ResultDto().makeResult(HttpStatus.FORBIDDEN, "로그인 실패");
        }
	}
	@RequestMapping(value = "/api/authorizeTokenRefresh" )
	public ResultDto<?> authorizeTokenRefresh(HttpServletRequest request) {
		String refreshToken = request.getHeader(AUTHORIZATION_HEADER);
		JwtTokenDto jwtTokenDto = tokenProvider.refreshToken(refreshToken.substring(7));
		if(jwtTokenDto != null) {
        	return new ResultDto().makeResult(HttpStatus.OK, "로그인 성공", jwtTokenDto, "jwtTokenDto");
        } else {
        	return new ResultDto().makeResult(HttpStatus.FORBIDDEN, "로그인 실패");
        }
	}
	
	@RequestMapping(value = "/api/register")
	public ResultDto<?> register(@RequestBody User user) {
		userService.register(user);
		return new ResultDto().makeResult(HttpStatus.OK, "테스트 성공");
	}
	@RequestMapping(value = "/api/selectAllUser")
	public ResultDto<?> selectAllUser() {
		return new ResultDto<>().makeResult(HttpStatus.OK, "테스트 성공", userService.selectAllUser(), "userList");
	}
}
