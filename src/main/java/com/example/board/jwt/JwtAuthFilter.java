package com.example.board.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = parseBearerToken(request);

        if (accessToken != null) {
            try {
                JwtPayload payload = jwtProvider.validateAndParseJwtPayload(accessToken);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        payload.getId(),
                        null,
                        payload.getRoles().stream()
                                .map(SimpleGrantedAuthority::new)
                                .toList());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                //TODO
            }
        }

        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION)).filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer ")).map(token -> token.substring(7)).orElse(null);
    }
}
