package com.kdt.prgrms.board.exception;

import org.springframework.http.ResponseEntity;

public class ErrorResponse {

    private final int status;
    private final String error;
    private final String code;
    private final String message;


    public ErrorResponse(int status, String error, String code, String message) {

        this.status = status;
        this.error = error;
        this.code = code;
        this.message = message;
    }

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {

        return ResponseEntity
                .status(errorCode.getStatus())
                .body(new ErrorResponse(
                        errorCode.getStatus().value(),
                        errorCode.getStatus().name(),
                        errorCode.name(),
                        errorCode.getMessage()
                        )
                );
    }

    public int getStatus() {

        return status;
    }

    public String getError() {

        return error;
    }

    public String getCode() {

        return code;
    }

    public String getMessage() {

        return message;
    }
}
