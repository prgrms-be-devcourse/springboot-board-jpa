package com.example.board.global.security.jwt.filter;

import com.example.board.global.exception.CustomException;
import com.example.board.global.exception.ErrorCode;
import com.example.board.global.security.jwt.provider.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.RedisConnectionFailureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String accessToken = extractTokenFromRequest(request);

        if (request.getRequestURI().startsWith("/login")) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            if(StringUtils.hasText(accessToken) && jwtTokenProvider.validateToken(accessToken)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.debug("SecurityContext에 '{}' 인증 정보를 저장했습니다, {}", authentication.getName(), accessToken);
            }
        } catch (RedisConnectionFailureException e) {
            SecurityContextHolder.clearContext();
            log.error("redis 서버 오류 {}", accessToken);
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        } catch (Exception e) {
            log.warn("유효한 JWT 토큰이 없습니다, {}", accessToken);
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        filterChain.doFilter(request, response);
    }

    private String extractTokenFromRequest(HttpServletRequest request) {
        String Bearer_token = request.getHeader("Authorization");
        return extractToken(Bearer_token);
    }

    private String extractToken(String bearer_token) {
        if (StringUtils.hasText(bearer_token) && bearer_token.startsWith("Bearer ")) {
            return bearer_token.substring(7);
        }
        return null;
    }
}
