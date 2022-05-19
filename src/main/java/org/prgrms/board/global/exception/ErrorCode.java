package org.prgrms.board.global.exception;

public enum ErrorCode {
    INVALID_INPUT_VALUE(400, "C001", "유효하지 않은 입력값입니다."),
    METHOD_NOT_ALLOWED(405, "C002", "해당 HTTP 메소드를 지원하지 않습니다."),
    POST_NOT_EXIST(400, "P001", "존재하지 않는 게시글입니다."),
    USER_NOT_EXIST(400, "U001", "존재하지 않는 사용자입니다."),
    EMAIL_NOT_EXIST(400, "E001", "존재하지 않는 이메일입니다."),
    PASSWORD_INCORRECT(400, "PW001", "비밀번호가 일치하지 않습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }
}
