package com.prgrms.boardjpa.global.response;

import com.prgrms.boardjpa.global.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ExceptionResponse<T> {

    private final HttpStatus httpStatus;
    private final String message;
    private final Map<String, String> valid;

    @Builder
    public ExceptionResponse(HttpStatus httpStatus, String message, Map<String, String> valid) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.valid = valid != null ? valid : new HashMap<>();
    }

    public static ResponseEntity<ExceptionResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ExceptionResponse.builder()
                        .httpStatus(errorCode.getStatus())
                        .message(errorCode.getMessage())
                        .build());
    }

    public void addValidation(String filedName, String errorMessage) {
        this.valid.put(filedName, errorMessage);
    }
}