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
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtRefreshAuthFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String refreshToken = parseBearerToken(request);

        if (isValidRequest(request, refreshToken)) {
            try {
                final JwtPayload payload = jwtProvider.validateAndParseRefreshTokenPayload(refreshToken);
                final Long userId = payload.getUserId();
                final List<String> roles = payload.getRoles();

                //TODO: isValidRefreshToken - 캐시에 저장되어 있는 토큰 값과 비교 후 아래 로직 수행하도록

                userRepository.findByIdAndDeletedAt(userId, null)
                        .orElseThrow(() -> new CustomException(CustomError.LOGIN_REQUIRED));

                JwtAuthToken authentication = new JwtAuthToken(userId, roles);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (JwtException | CustomException e) {
                throw new CustomException(CustomError.INVALID_TOKEN);
            }
        }

        filterChain.doFilter(request, response);
    }

    private Boolean isValidRequest(HttpServletRequest request, String refreshToken) {
        return pathMatcher.match("/api/*/users/refresh", request.getRequestURI()) && refreshToken != null;
    }

    private String parseBearerToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(token -> token.startsWith("Bearer "))
                .map(token -> token.substring(7))
                .orElseThrow(() -> new CustomException(CustomError.INVALID_TOKEN));
    }
}
