package com.example.jkds.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.jkds.security.jwt.JwtAccessDeniedHandler;
import com.example.jkds.security.jwt.JwtAuthenticationEntryPoint;
import com.example.jkds.security.jwt.JwtSecurityConfig;
import com.example.jkds.security.jwt.TokenProvider;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
   
	public SecurityConfig(TokenProvider tokenProvider,
			JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, JwtAccessDeniedHandler jwtAccessDeniedHandler) {
		super();
		this.tokenProvider = tokenProvider;
		this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
		this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers(
        		"/src/main/resources/**");
    }
    
    @Bean
	SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity
    			.cors()
    		.and()
	    		.csrf()
	    		.disable()
	        /**401, 403 Exception 핸들링 */
	        .exceptionHandling()
	        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
	        .accessDeniedHandler(jwtAccessDeniedHandler)
	        /**세션 사용하지 않음*/
	        .and()
		        .sessionManagement()
		        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
	        	/** HttpServletRequest를 사용하는 요청들에 대한 접근 제한 설정*/
	        .and()
		        .authorizeHttpRequests()
		        .requestMatchers("/api/authorize").permitAll()
		        .requestMatchers("/api/register").permitAll()
		        .anyRequest().authenticated()
		    .and()
	        	/**JwtSecurityConfig 적용 */
	        	.apply(new JwtSecurityConfig(tokenProvider));
    	
    	return httpSecurity.build();
    }
}
