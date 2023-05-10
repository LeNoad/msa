package com.example.jkds.security.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.example.jkds.cmm.dto.JwtTokenDto;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenProvider{
	protected static final String AUTHORITIES_KEY = "Authorization";
	
	protected final String accessTokenSecret;
	protected final long accessTokenValidityInSeconds;
	
	private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 3;  // 3시간
	
	protected Key key;
	
	public TokenProvider(String accessTokenSecret, long accessTokenValidityInSeconds) {
		this.accessTokenSecret = accessTokenSecret;
		this.accessTokenValidityInSeconds = accessTokenValidityInSeconds * 1000;

		byte[] keyBytes = Decoders.BASE64.decode(accessTokenSecret);
		this.key = Keys.hmacShaKeyFor(keyBytes); 
		log.info(" Token Key : " +key.toString());
	}
	public JwtTokenDto refreshToken(String refreshTokn) {
			Authentication authentication = getAuthentication(refreshTokn);
			String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
			long now = (new Date()).getTime();
			Date validity = new Date(now + this.accessTokenValidityInSeconds);
			Date refreshValidity = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
			String accessToken = Jwts.builder()
					.setSubject(authentication.getName())
					.claim(AUTHORITIES_KEY, authorities)
					.signWith(key, SignatureAlgorithm.HS512)
					.setExpiration(validity)
					.compact();
			String refreshToken = Jwts.builder()
					.setSubject(authentication.getName())
					.claim(AUTHORITIES_KEY, authorities)
					.signWith(key, SignatureAlgorithm.HS512)
					.setExpiration(refreshValidity)
					.compact();
			return new JwtTokenDto(accessToken, refreshToken, refreshValidity);
	}
	public JwtTokenDto createToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(","));
		long now = (new Date()).getTime();
		Date validity = new Date(now + this.accessTokenValidityInSeconds);
		Date refreshValidity = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
		String accessToken = Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(validity)
				.compact();
		String refreshToken = Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(key, SignatureAlgorithm.HS512)
				.setExpiration(refreshValidity)
				.compact();
		return new JwtTokenDto(accessToken, refreshToken, refreshValidity);
	}
	
	public Authentication getAuthentication(String token) {
		Claims claims = Jwts
				.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toList());
		User principal = new User(claims.getSubject(), "", authorities);
		
		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}
	
	// 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
        	log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
        	log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
        	log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
