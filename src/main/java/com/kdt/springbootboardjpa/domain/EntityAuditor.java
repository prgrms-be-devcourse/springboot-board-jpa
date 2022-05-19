package com.kdt.springbootboardjpa.domain;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

@Component
public class EntityAuditor implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        var request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        String uri = request.getRequestURI();
        if (uri.startsWith("/api")) {
            return Optional.of("api");
        }
        return Optional.of("test");
    }
}