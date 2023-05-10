package com.example.jkds.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.jkds.user.entity.User;
import com.example.jkds.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserDetailService implements UserDetailsService{
	@Autowired
	public UserRepository userRepository;

	@Transactional
	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userRepository.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException(userId + " -> 데이터 베이스에서 찾을 수 없습니다."));
		return CustomUserPrincipal.create(user);
	}
}
