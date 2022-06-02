package com.prgrms.springboard.global.error.exception;

import org.springframework.http.HttpStatus;

public class PermissionException extends BusinessException {
    public PermissionException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
