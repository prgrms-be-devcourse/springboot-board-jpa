package com.study.board.domain.exception;

import java.text.MessageFormat;

public class PostNotFoundException extends BoardRuntimeException {

    public PostNotFoundException(Long id) {
        super(MessageFormat.format("post of id : {0} not found", id));
    }
}
