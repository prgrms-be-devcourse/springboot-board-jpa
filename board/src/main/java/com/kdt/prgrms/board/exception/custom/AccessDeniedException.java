package com.kdt.prgrms.board.exception.custom;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends RuntimeException {

    private String message;
    private HttpStatus status;

    public AccessDeniedException() {

    }

    @Override
    public String getMessage() {

        return message;
    }

    public HttpStatus getStatus() {

        return status;
    }
}
