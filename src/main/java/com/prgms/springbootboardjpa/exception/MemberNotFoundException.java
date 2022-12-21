package com.prgms.springbootboardjpa.exception;

import static com.prgms.springbootboardjpa.exception.ExceptionMessage.USER_NOT_FOUND;

public class MemberNotFoundException extends RuntimeException {
    @Override
    public String getMessage() {
        return USER_NOT_FOUND.getMessage();
    }
}