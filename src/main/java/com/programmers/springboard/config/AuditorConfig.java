package com.programmers.springboard.config;

import java.util.Optional;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
public class AuditorConfig implements AuditorAware<String> {
	@Override
	public Optional<String> getCurrentAuditor() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
		String uid = request.getHeader("uid");
		return Optional.ofNullable(uid);
	}
}
