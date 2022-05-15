package com.study.board.domain.exception;

import java.text.MessageFormat;

public class UserNotFoundException extends BoardRuntimeException {

    public UserNotFoundException(Long id) {
        super(MessageFormat.format("user of id : {0} not found", id));
    }
}