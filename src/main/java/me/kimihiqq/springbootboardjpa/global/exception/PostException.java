package me.kimihiqq.springbootboardjpa.global.exception;

import lombok.Getter;
import me.kimihiqq.springbootboardjpa.global.error.ErrorCode;

@Getter
public class PostException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String debugMessage;

    public PostException(ErrorCode errorCode, String debugMessage) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.debugMessage = debugMessage;
    }
}
