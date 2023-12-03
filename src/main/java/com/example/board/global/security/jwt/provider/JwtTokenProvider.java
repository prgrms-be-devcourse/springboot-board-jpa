package com.example.board.global.security.jwt.provider;

import com.example.board.global.exception.CustomException;
import com.example.board.global.exception.ErrorCode;
import com.example.board.global.security.details.MemberDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private static final String AUTHORITY_ID = "id";
    private static final String AUTHORITY_KEY = "auth";
    private static final String AUTHORITY_EMAIL = "email";

    private final long tokenValidityInMilliseconds;
    private final String secret;
    private Key key;

    public JwtTokenProvider(
        @Value("${jwt.token-validity-in-milliseconds}") long tokenValidityInMilliseconds,
        @Value("${jwt.secret_key}") String secret) {

        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds;
        this.secret = secret;
    }

    @PostConstruct
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(Authentication authentication) {
        MemberDetails principal = (MemberDetails) authentication.getPrincipal();

        String authorities = principal.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","));

        Date now = new Date();
        Date validity = new Date(now.getTime() + this.tokenValidityInMilliseconds);

        return Jwts.builder()
            .setSubject(authentication.getName())
            .addClaims(Map.of(
                AUTHORITY_ID, principal.getId(),
                AUTHORITY_EMAIL, principal.getEmail(),
                AUTHORITY_KEY, authorities)
            )
            .signWith(key, SignatureAlgorithm.HS512)
            .setIssuedAt(now)
            .setExpiration(validity)
            .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();

        List<? extends GrantedAuthority> authorities = Arrays.stream(claims.get(AUTHORITY_KEY)
                .toString()
                .split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();

        MemberDetails principal = new MemberDetails((String) claims.get(AUTHORITY_ID), (String) claims.get(
            AUTHORITY_EMAIL) ,null, authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException | IllegalArgumentException e) {
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new CustomException(ErrorCode.EXPIRED_TOKEN);
        } catch(UnsupportedJwtException e) {
            throw new CustomException(ErrorCode.UNSUPPORTED_JWT);
        }
    }
}
