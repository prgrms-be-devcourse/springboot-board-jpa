package com.springbootboardjpa.common;

public class NoSuchEntityException extends RuntimeException {

    public NoSuchEntityException(String message) {
        super(message);
    }

    public NoSuchEntityException() {
        this("해당 엔티티가 존재하지 않습니다.");
    }
}
