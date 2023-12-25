package com.example.board.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private final String issuer;
    private final String accessSecretKey;
    private final Long accessExpirySeconds;
    private final String refreshSecretKey;
    private final Long refreshExpirySeconds;
}
