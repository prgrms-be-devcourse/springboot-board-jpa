package com.example.board.auth.utils;

import com.example.board.dto.response.ApiResponse;
import com.example.board.dto.response.ResponseStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class HttpResponseUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void setResponse(HttpServletResponse response, ResponseStatus responseStatus) throws IOException {
        String responseBody = objectMapper.writeValueAsString(ApiResponse.fail(responseStatus));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(responseStatus.getHttpStatus().value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }

    public static void setResponse(HttpServletResponse response, ResponseStatus responseStatus, Object body) throws IOException {
        String responseBody = objectMapper.writeValueAsString(ApiResponse.fail(responseStatus, body));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(responseStatus.getHttpStatus().value());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(responseBody);
    }
}
