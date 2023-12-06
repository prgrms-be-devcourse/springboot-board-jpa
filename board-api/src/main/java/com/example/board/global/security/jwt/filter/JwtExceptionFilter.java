package com.example.board.global.security.jwt.filter;

import com.example.board.global.advisor.ErrorResponse;
import com.example.board.global.exception.CustomException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            log.warn(e.getMessage());
            createResponse(response, e);
        }
    }
    private void createResponse(HttpServletResponse response, CustomException e) {
        response.setStatus(e.getErrorCode().getStatus().value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode().getMessage());
        try {
            String json = new ObjectMapper().writeValueAsString(errorResponse);
            response.getWriter().write(json);
        } catch (Exception ex) {
            log.error(ex.getMessage());
        }
    }
}
