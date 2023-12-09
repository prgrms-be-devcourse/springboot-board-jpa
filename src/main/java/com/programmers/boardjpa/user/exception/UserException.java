package com.programmers.boardjpa.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class UserException extends RuntimeException {
    private final UserErrorCode errorCode;

    public UserException(UserErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public UserException(UserErrorCode errorCode, Long id) {
        this.errorCode = errorCode;
        this.errorCode.addIdToMessage(id);
    }
}
