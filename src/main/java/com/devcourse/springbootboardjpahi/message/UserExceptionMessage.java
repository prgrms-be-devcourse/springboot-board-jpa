package com.devcourse.springbootboardjpahi.message;

public final class UserExceptionMessage {

    public final static String BLANK_NAME = "이름은 공백일 수 없습니다.";
    public final static String NEGATIVE_AGE = "나이는 음수일 수 없습니다.";

    private UserExceptionMessage() {
        // Don't let anyone instantiate this class.
    }
}
