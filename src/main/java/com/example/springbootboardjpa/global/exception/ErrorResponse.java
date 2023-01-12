package com.example.springbootboardjpa.global.exception;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private final LocalDateTime timestamp = LocalDateTime.now();
  private final Integer status;
  private final String error;
  private final String code;
  private final String message;

  public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {

    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(
            ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .error(errorCode.getHttpStatus().name())
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .build());
  }

  public static ResponseEntity<Object> toObject(CustomException customException) {
    ErrorCode errorCode = customException.getErrorCode();

    return ResponseEntity
        .status(errorCode.getHttpStatus())
        .body(
            ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .error(errorCode.getHttpStatus().name())
                .code(errorCode.name())
                .message(customException.getMessage())
                .build());
  }
}
