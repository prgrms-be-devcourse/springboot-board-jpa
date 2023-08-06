package com.prgrms.board.common.entity;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Component
public class CustomAuditAware implements AuditorAware<String>, DateTimeProvider {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("Yu-and-Ahn");
    }

    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(LocalDateTime.now());
    }
}
