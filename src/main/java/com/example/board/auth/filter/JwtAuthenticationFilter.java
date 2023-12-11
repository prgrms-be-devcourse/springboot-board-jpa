package com.example.board.auth.filter;

import com.example.board.auth.domain.RefreshToken;
import com.example.board.auth.dto.response.JwtToken;
import com.example.board.auth.provider.JwtTokenProvider;
import com.example.board.auth.repository.RefreshTokenRepository;
import com.example.board.auth.utils.HttpResponseUtil;
import com.example.board.dto.response.ResponseStatus;
import com.example.board.exception.CustomException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    private final RefreshTokenRepository refreshTokenRepository;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, RefreshTokenRepository refreshTokenRepository) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.refreshTokenRepository = refreshTokenRepository;
    }

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws IOException, ServletException {
        String accessToken = resolveToken(request);
        try {
            jwtTokenProvider.validateAccessToken(accessToken);
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            RefreshToken tokenInfo = refreshTokenRepository.findByAccessToken(accessToken)
                    .orElseThrow(() -> new CustomException(ResponseStatus.REFRESH_TOKEN_NOT_FOUND));

            String refreshToken = tokenInfo.getRefreshToken();
            jwtTokenProvider.validateAccessToken(refreshToken);

            Long userId = Long.valueOf(tokenInfo.getId());
            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            final JwtToken newToken = jwtTokenProvider.generateToken(authentication);

            refreshTokenRepository.save(new RefreshToken(String.valueOf(userId), refreshToken, accessToken));
            HttpResponseUtil.setResponse(response, ResponseStatus.TOKEN_REGENERATED, newToken);
        }
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
