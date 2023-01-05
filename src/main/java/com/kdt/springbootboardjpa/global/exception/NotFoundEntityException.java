package com.kdt.springbootboardjpa.global.exception;

public class NotFoundEntityException extends RuntimeException {

    public NotFoundEntityException(ExceptionMessage message) {
        super(message.getMessage());
    }
}
