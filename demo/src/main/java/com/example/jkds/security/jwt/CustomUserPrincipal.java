package com.example.jkds.security.jwt;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.jkds.user.entity.User;

import lombok.Getter;

@Getter
public class CustomUserPrincipal implements UserDetails{
	private Integer userIdx;
	private String userId;
	private String userPassword;
	
	private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;
    
	public CustomUserPrincipal(Integer userIdx, String userId, String userPassword,
			Collection<? extends GrantedAuthority> authorities) {
		this.userIdx = userIdx;
		this.userId = userId;
		this.userPassword = userPassword;
		this.authorities = authorities;
	}
	public static CustomUserPrincipal create(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(user.getUserId()));
        return new CustomUserPrincipal(user.getUserIdx(), user.getUserId(), user.getUserPassword(), authorities);
    }
	@Override
	public String getUsername() {
		return userId;
	}
	@Override
	public String getPassword() {
		return userPassword;
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}
	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
}
