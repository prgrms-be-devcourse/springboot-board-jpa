package com.example.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 장애가 발생했습니다.");

    private int code;
    private HttpStatus httpStatus;
    private String message;
}
