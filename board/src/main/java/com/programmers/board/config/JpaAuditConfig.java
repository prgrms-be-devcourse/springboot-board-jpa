package com.programmers.board.config;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
public class JpaAuditConfig {
	@Bean
	public AuditorAware<String> autAuditorProvider(HttpServletRequest servlet) {
		return () -> Optional.of(servlet.getRemoteAddr());
	}
}
