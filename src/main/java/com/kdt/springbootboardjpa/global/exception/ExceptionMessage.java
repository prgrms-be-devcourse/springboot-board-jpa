package com.kdt.springbootboardjpa.global.exception;

public enum ExceptionMessage {
    // Member
    MEMBER_NOT_EXIST("존재하지 않는 회원입니다."),

    // Post
    POST_NOT_EXIST("존재하지 않는 게시판입니다.");

    private final String message;

    ExceptionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
