package com.programmers.springbootboard.handler;

import com.programmers.springbootboard.dto.ApiResponse;
import com.programmers.springbootboard.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletRequest;
import java.util.Objects;

@RestControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(value = {NotFoundException.class, IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponseDto> resourceNotFoundException(Exception e, ServletWebRequest request) {
        return ApiResponse.<ErrorResponseDto>builder()
                .success(false)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .httpMethod(Objects.requireNonNull(request.getHttpMethod()).toString())
                .data(ErrorResponseDto.builder().errorMessage(e.getMessage()).build())
                .build();
    }

    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<ErrorResponseDto> internalServerError(Exception e, ServletWebRequest request) {
        return ApiResponse.<ErrorResponseDto>builder()
                .success(false)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .httpMethod(Objects.requireNonNull(request.getHttpMethod()).toString())
                .data(ErrorResponseDto.builder().errorMessage(e.getMessage()).build())
                .build();
    }
}
