package com.programmers.boardjpa.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException {
    private final HttpStatus errorCode;

    public UserException(UserErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode.getErrorCode();
    }

    public UserException(UserErrorCode errorCode, Long id) {
        super(errorCode.getErrorMessage() + " : " + id);
        this.errorCode = errorCode.getErrorCode();
    }
}
