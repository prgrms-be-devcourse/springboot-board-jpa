package com.board.server.global.exception.model;

import com.board.server.global.exception.Error;

public class BadRequestException extends CustomException {
    public BadRequestException(Error error, String message) {
        super(error, message);
    }
}
