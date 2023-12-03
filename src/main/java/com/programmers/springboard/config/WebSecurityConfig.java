package com.programmers.springboard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.programmers.springboard.config.jwt.Jwt;
import com.programmers.springboard.config.jwt.JwtAuthenticationFilter;
import com.programmers.springboard.config.jwt.JwtAuthenticationProvider;
import com.programmers.springboard.repository.MemberRepository;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtConfig jwtConfig;

	@Bean
	public Jwt jwt() {
		return new Jwt(jwtConfig.getIssuer(), jwtConfig.getClientSecret(), jwtConfig.getExpirySeconds());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationProvider jwtAuthenticationProvider(
		Jwt jwt,
		MemberRepository memberRepository
	) {
		return new JwtAuthenticationProvider(jwt, memberRepository, passwordEncoder());
	}

	@Bean
	public AuthenticationManager authenticationManager(JwtAuthenticationProvider jwtAuthenticationProvider) {
		return new ProviderManager(jwtAuthenticationProvider);
	}

	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtConfig.getHeader(), jwt());
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			// 권한 설정
			.authorizeHttpRequests(authorize -> authorize
				// 게시글 관련 규칙
				.requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**", "GET")).authenticated() // 게시글 조회는 인증된 유저만 허용
				.requestMatchers(new AntPathRequestMatcher("/api/v1/posts/**")).hasAnyRole("USER", "ADMIN") // 게시글 작성, 수정, 삭제는 USER, ADMIN 권한만 허용

				// 회원 관련 규칙
				.requestMatchers(new AntPathRequestMatcher("/api/v1/members/sign-in", "POST")).permitAll() // 로그인은 모두 허용
				.requestMatchers(new AntPathRequestMatcher("/api/v1/members", "POST")).permitAll() // 회원 가입도 모두 허용
				.requestMatchers(new AntPathRequestMatcher("/api/v1/members/**")).hasAnyRole("USER", "ADMIN") // 회원 조회, 수정, 삭제는 USER, ADMIN 권한만 허용

				// 나머지 요청에 대한 규칙
				.anyRequest().permitAll() // 나머지 모든 요청은 인증 필요
			)
			// 예외 처리
			.exceptionHandling(exceptionHandling -> exceptionHandling
				// 401 UNAUTHORIZED
				.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))

				// 403 FORBIDDEN
				.accessDeniedHandler((request, response, accessDeniedException) -> {
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					response.setContentType("application/json");
					response.getWriter().write("{ \"error\": \"Access Denied\" }");
				})
			)
			// 폼 로그인 비활성화
			.formLogin(Customizer.withDefaults())
			// 로그아웃 비활성화
			.logout(AbstractHttpConfigurer::disable)
			// CSRF 보호 비활성화
			.csrf(AbstractHttpConfigurer::disable)
			// 헤더 보안 설정 비활성화
			.headers(AbstractHttpConfigurer::disable)
			// HTTP 기본 인증 비활성화
			.httpBasic(AbstractHttpConfigurer::disable)
			// 세션 관리 설정
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			// JWT 인증 필터 추가
			.addFilterAfter(jwtAuthenticationFilter(), SecurityContextHolderFilter.class);

		return http.build();
	}

}
