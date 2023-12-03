package com.programmers.springboard.global.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableJpaAuditing
public class JpaAuditingConfig implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		try {
			HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
			String token = request.getHeader("token");
			if (token != null) {
				String username = getUsernameFromToken(token);
				return Optional.ofNullable(username);
			}
		} catch (IllegalStateException e) {
			// HTTP 요청 컨텍스트가 없는 경우 (예: 배치 작업)
			// 여기서는 정적 사용자 이름을 반환하거나 다른 로직 적용
			return Optional.of("default_auditor"); // 예시: 정적 사용자 이름
		}
		return Optional.empty();
	}

	private String getUsernameFromToken(String token) {
		try {
			DecodedJWT decodedJWT = JWT.decode(token);
			return decodedJWT.getClaim("username").asString();
		} catch (JWTDecodeException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

}
