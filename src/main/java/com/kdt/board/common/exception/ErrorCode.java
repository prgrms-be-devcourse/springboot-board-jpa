package com.kdt.board.common.exception;

public enum ErrorCode {

    INVALID_INPUT_VALUE(400, "적절하지 않은 요청 값입니다."),
    INVALID_TYPE_VALUE(400,  "요청 값의 타입이 잘못되었습니다."),
    USER_NOT_FOUND(400, "유저를 찾을 수 없습니다."),
    POST_NOT_FOUND(400, "글을 찾을 수 없습니다."),
    POST_EDIT_UNAUTHORIZED(403, "글을 수정할 수 있는 권한이 없습니다."),

    INTERNAL_SERVER_ERROR(500,  "서버 내부에 오류가 생겼습니다.");

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
