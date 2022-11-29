package com.springboard.common.exception;

public class FindFailException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "데이터를 찾을 수 없습니다.";

    public FindFailException() {
        this(DEFAULT_MESSAGE);
    }

    public FindFailException(String message) {
        super(message);
    }
}
