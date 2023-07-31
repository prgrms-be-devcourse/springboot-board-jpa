package com.board.server.global.exception.model;

import com.board.server.global.exception.Error;

public class NotFoundException extends CustomException {
    public NotFoundException(Error error, String message) {
        super(error, message);
    }
}