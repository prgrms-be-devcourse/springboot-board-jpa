package com.devcourse.springbootboardjpahi.message;

public final class PostExceptionMessage {

    public final static String BLANK_TITLE = "제목은 공백일 수 없습니다.";
    public final static String NULL_CONTENT = "내용이 존재하지 않습니다.";
    public final static String INVALID_USER_ID = "유효하지 않은 유저 아이디 입니다.";
    public final static String NO_SUCH_USER = "존재하지 않는 유저 입니다.";
    public final static String NO_SUCH_POST = "존재하지 않는 게시글 입니다.";

    private PostExceptionMessage() {
        // Don't let anyone instantiate this class.
    }
}
