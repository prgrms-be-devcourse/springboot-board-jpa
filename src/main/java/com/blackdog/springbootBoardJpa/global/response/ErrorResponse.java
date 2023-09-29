package com.blackdog.springbootBoardJpa.global.response;

public class ErrorResponse {
    private final String code;
    private final String message;

    public ErrorResponse(
            final String code,
            final String message
    ) {
        this.code = code;
        this.message = message;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(
                errorCode.getCode(),
                errorCode.getMessage()
        );
    }

    @Override
    public String toString() {
        return "ErrorResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
