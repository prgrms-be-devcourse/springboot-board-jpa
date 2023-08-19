package com.programmers.jpa_board.global.exception;

public enum ExceptionMessage {
    NOT_FOUND_POST("게시글이 존재하지 않습니다."),
    NOT_FOUND_USER("회원이 존재하지 않습니다."),
    INVALID_NAME("이름 형식이 잘못 되었습니다."),
    INVALID_AGE("나이가 잘못 입력 되었습니다."),
    INVALID_TITLE("제목 형식이 잘못 되었습니다.")
    ;

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
