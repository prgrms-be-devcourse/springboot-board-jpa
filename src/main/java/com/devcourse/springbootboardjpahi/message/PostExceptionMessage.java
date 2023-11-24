package com.devcourse.springbootboardjpahi.message;

public final class PostExceptionMessage {

    public final static String BLANK_TITLE = "제목은 공백일 수 없습니다.";
    public final static String NULL_CONTENT = "내용이 존재하지 않습니다.";
    public final static String INVALID_USER_ID = "유효하지 않은 유저 아이디입니다.";

    private PostExceptionMessage() {
        // Don't let anyone instantiate this class.
    }
}
