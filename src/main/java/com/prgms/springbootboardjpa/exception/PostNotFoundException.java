package com.prgms.springbootboardjpa.exception;

import static com.prgms.springbootboardjpa.exception.ExceptionMessage.POST_NOT_FOUND;

public class PostNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return POST_NOT_FOUND.getMessage();
    }
}
