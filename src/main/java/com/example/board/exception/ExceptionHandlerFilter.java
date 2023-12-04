package com.example.board.exception;

import com.example.board.dto.response.ApiResponse;
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
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    private ExceptionHandlerFilter() {
        this.objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (CustomException e) {
            CustomError customError = e.getCustomError();
            sendFailResponse(response, customError);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            CustomError customError = CustomError.INTERNAL_SERVER_ERROR;
            sendFailResponse(response, customError);
        }
    }

    private void sendFailResponse(HttpServletResponse response, CustomError customError) throws IOException {
        response.setStatus(customError.getStatus().value());
        response.setContentType("application/json");

        ErrorResponse errorResponse = new ErrorResponse(customError.getCode(), customError.getMessage());
        response.getWriter().write(objectMapper.writeValueAsString(ApiResponse.fail(errorResponse)));
    }
}
