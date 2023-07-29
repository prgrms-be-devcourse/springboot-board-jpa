package com.board.server.global.exception.model;

import com.board.server.global.exception.Error;
import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final Error error;

    public CustomException(Error error, String message) {
        super(message);
        this.error = error;
    }

    public int getHttpStatus() {
        return error.getHttpStatusCode();
    }
}
