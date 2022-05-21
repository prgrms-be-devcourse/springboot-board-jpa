package com.kdt.board.common.exception;

public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "적절하지 않은 요청 값입니다.");

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
