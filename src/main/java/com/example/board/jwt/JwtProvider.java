package com.example.board.jwt;

import com.example.board.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class JwtProvider {
    private final String issuer;
    private final SecretKey accessSecretKey;
    private final Long accessExpirySeconds;
    private final SecretKey refreshSecretKey;
    private final Long refreshExpirySeconds;

    public JwtProvider(JwtConfig jwtConfig) {
        this.issuer = jwtConfig.getIssuer();
        this.accessSecretKey = Keys.hmacShaKeyFor(jwtConfig.getAccessSecretKey().getBytes());
        this.accessExpirySeconds = jwtConfig.getAccessExpirySeconds();
        this.refreshSecretKey = Keys.hmacShaKeyFor(jwtConfig.getRefreshSecretKey().getBytes());
        this.refreshExpirySeconds = jwtConfig.getRefreshExpirySeconds();
    }

    public String generateAccessToken(JwtPayload payload) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationMilliSeconds = now.plusSeconds(accessExpirySeconds);
        return Jwts.builder()
                .issuer(issuer)
                .issuedAt(convertToDate(now))
                .expiration(convertToDate(expirationMilliSeconds))
                .claims(new HashMap<>(payload.toMap()))
                .signWith(accessSecretKey)
                .compact();
    }

    public String generateRefreshToken(JwtPayload payload) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationMilliSeconds = now.plusSeconds(refreshExpirySeconds);
        return Jwts.builder()
                .issuer(issuer)
                .issuedAt(convertToDate(now))
                .expiration(convertToDate(expirationMilliSeconds))
                .claims(new HashMap<>(payload.toMap()))
                .signWith(refreshSecretKey)
                .compact();
    }

    public JwtPayload validateAndParseJwtPayload(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(accessSecretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return new JwtPayload(claims.get("userId", Long.class), claims.get("roles", List.class));
    }

    private Date convertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }
}
