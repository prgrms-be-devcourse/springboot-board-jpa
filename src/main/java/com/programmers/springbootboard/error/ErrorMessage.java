package com.programmers.springbootboard.error;

import java.util.Arrays;

public enum ErrorMessage {
    INVALID_MEMBER_NAME("이름 형식이 맞지 않습니다."),
    INVALID_MEMBER_EMAIL("이메일 형식이 맞지 않습니다."),
    INVALID_MEMBER_AGE("나이 형식이 맞지 않습니다."),
    INVALID_MEMBER_HOBBY("취미 형식이 맞지 않습니다."),
    NOT_EXIST_MEMBER("존재하지 않는 회원입니다."),
    INTERNAL_SERVER_ERROR("정의되지 않은 서버 에러"),
    INVALID_POST_TITLE("제목 형식이 맞지 않습니다."),
    DUPLICATION_MEMBER_EMAIL("중복된 이메일입니다."),
    NOT_EXIST_POST("존재하지 않는 게시글 입니다."),
    UNSUPPORTED_MEDIA_TYPE("지원하지 않는 미디어 타입입니다.");

    private final String message;

    ErrorMessage(String message) {

        this.message = message;
    }

    public String message() {
        return message;
    }

    public static ErrorMessage of(String errorMessage) {
        return Arrays.stream(values())
                .filter(e -> e.message.equals(errorMessage))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(ErrorMessage.INTERNAL_SERVER_ERROR.message()));
    }
}
