package com.example.board.jwt;

import com.example.board.exception.CustomError;
import com.example.board.exception.CustomException;
import com.example.board.repository.user.UserRepository;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String accessToken = parseBearerToken(request);

        if (isValidRequest(request, accessToken)) {
            try {
                final JwtPayload payload = jwtProvider.validateAndParseAccessTokenPayload(accessToken);
                final Long userId = payload.getUserId();
                final List<String> roles = payload.getRoles();

                userRepository.findByIdAndDeletedAt(userId, null)
                        .orElseThrow(() -> new CustomException(CustomError.INVALID_TOKEN));

                JwtAuthToken authentication = new JwtAuthToken(userId, roles);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException | CustomException e) {
                throw new CustomException(CustomError.INVALID_TOKEN);
            }
        }

        filterChain.doFilter(request, response);
    }

    private Boolean isValidRequest(HttpServletRequest request, String accessToken) {
        return !pathMatcher.match("/api/*/users/refresh", request.getRequestURI()) && accessToken != null;
    }

    private String parseBearerToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.startsWith("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }
}
