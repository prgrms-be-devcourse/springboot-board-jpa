package org.programmers.dev.exception;

public class PostNotFoundException extends RuntimeException {

    private static String MESSAGE = " : 해당 아이디 값을 가진 post가 없습니다.";

    public PostNotFoundException(String value) {
        super(value + MESSAGE);
    }

}
