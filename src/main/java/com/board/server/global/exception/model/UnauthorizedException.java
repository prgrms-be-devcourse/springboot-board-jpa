package com.board.server.global.exception.model;

import com.board.server.global.exception.Error;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(Error error, String message) {
        super(error, message);
    }
}
