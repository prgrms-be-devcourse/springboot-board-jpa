package com.example.board.auth.provider;

import com.example.board.auth.domain.CustomUserDetails;
import com.example.board.auth.dto.response.TokenResponse;
import com.example.board.auth.exception.TokenException;
import com.example.board.dto.response.ResponseStatus;
import com.example.board.exception.CustomException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    private static final String AUTHORITIES_CLAIM = "auth";
    private static final String AUTHORITIES_SEPARATOR = ",";

    private final Long accessExpirationTimes;
    private final Long refreshExpirationTimes;
    private final Key secret;

    public JwtTokenProvider(
            @Value("${JWT_SECRET_KEY}") String secretKey,
            @Value("${JWT_ACCESS_TOKEN_EXPIRATION_TIME}") Long accessExpirationTimes,
            @Value("${JWT_REFRESH_TOKEN_EXPIRATION_TIME}") Long refreshExpirationTimes
    ) {

        this.accessExpirationTimes = accessExpirationTimes;
        this.refreshExpirationTimes = refreshExpirationTimes;
        byte[] secretByteKey = Base64.getDecoder().decode(secretKey);
        this.secret = Keys.hmacShaKeyFor(secretByteKey);
    }

    public TokenResponse generateToken(Authentication authentication) {
        return new TokenResponse(
                "Bearer",
                generateAccessToken(authentication),
                generateRefreshToken());
    }

    public TokenResponse regenerateToken(String accessToken) {
        final Authentication authentication = getAuthentication(accessToken);
        return generateToken(authentication);
    }

    public String generateAccessToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(AUTHORITIES_SEPARATOR));

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_CLAIM, authorities)
                .setExpiration(new Date(System.currentTimeMillis() + accessExpirationTimes))
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken() {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpirationTimes))
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
    }

    public void validateToken(String accessToken) {
        try {
            Jwts.parserBuilder().setSigningKey(secret).build().parseClaimsJws(accessToken);
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new TokenException(ResponseStatus.INVALID_SIGNATURE_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new TokenException(ResponseStatus.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new TokenException(ResponseStatus.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new TokenException(ResponseStatus.EMPTY_CLAIMS_TOKEN);
        } catch (Exception e) {
            throw new TokenException(ResponseStatus.TOKEN_PROCESSING_ERROR);
        }
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);

        if (claims.getSubject() == null) {
            throw new CustomException(ResponseStatus.UNAUTHORIZED);
        }
        if (claims.get("auth") == null) {
            throw new CustomException(ResponseStatus.MISSING_AUTHORIZATION_CLAIM);
        }

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_CLAIM).toString().split(AUTHORITIES_SEPARATOR))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails principal = CustomUserDetails.builder()
                .id(claims.getSubject())
                .password("")
                .authorities(authorities)
                .build();

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(secret).build()
                    .parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
