package com.example.board.auth.filter;

import com.example.board.auth.exception.TokenException;
import com.example.board.dto.response.ApiResponse;
import com.example.board.dto.response.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (TokenException e) {
            // 토큰 필터에서 발생한 예외 처리
            log.warn("Exception while token filter processing. Request Uri : {}", request.getRequestURI());
            final ResponseStatus responseStatus = e.getErrorResponseStatus();
            setResponse(response, responseStatus);
        } catch (Exception e) {
            // 예상치 못한 예외 처리
            log.error("Unexpected Exception. Request Uri : {}", request.getRequestURI(), e);
            setResponse(response, ResponseStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void setResponse(HttpServletResponse response, ResponseStatus responseStatus) throws IOException {
        String responseBody = objectMapper.writeValueAsString(ApiResponse.fail(responseStatus));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(responseStatus.getHttpStatus().value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }

}
