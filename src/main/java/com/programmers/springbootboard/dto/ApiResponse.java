package com.programmers.springbootboard.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

@Data
@Builder
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ApiResponse<T> {
    private Boolean success;
    private String httpMethod;
    private int statusCode;
    private T data;

    public static <T> ApiResponse<T> ok(HttpMethod httpMethod, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .httpMethod(httpMethod.name())
                .statusCode(HttpStatus.OK.value())
                .data(data)
                .build();
    }
}
