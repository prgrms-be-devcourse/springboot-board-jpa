package com.kdt.board.common.exception;

public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "적절하지 않은 요청 값입니다."),
    INVALID_TYPE_VALUE(400,  "요청 값의 타입이 잘못되었습니다.");

    private final int status;
    private final String message;

    ErrorCode(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public int getStatus() {
        return status;
    }
}
