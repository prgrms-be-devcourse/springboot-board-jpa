package com.example.boardbackend.common.error.response;

import lombok.Builder;
import lombok.Getter;

@Getter // Response에 Getter 필수 !!!!!!!!!!
@Builder
public class ErrorResponse {
    private String message;
    private String exceptionType;
}
