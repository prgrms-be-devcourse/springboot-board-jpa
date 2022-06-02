package org.programmers.springboardjpa.global.error.exception;

public enum ErrorCode {

    POST_NOT_FOUND(404,"P001","존재하지 않는 게시글입니다."),
    USER_NOT_FOUND(404,"U001","존재하지 않는 유저입니다."),

    INVALID_INPUT(400, "I001", "유효하지 않은 값입니다."),
    INVALID_TYPE(400, "I002", "유효하지 않은 타입입니다."),

    INTERNAL_SERVER_ERROR(500, "S001", "서버 에러입니다."),
    METHOD_NOT_ALLOWED(405, "S002", "허용되지 않은 HTTP 메소드 입니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

