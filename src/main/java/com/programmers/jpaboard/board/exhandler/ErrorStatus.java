package com.programmers.jpaboard.board.exhandler;

import lombok.Getter;
import org.springframework.web.bind.MethodArgumentNotValidException;

@Getter
public enum ErrorStatus {
    BOARD_NOT_FOUND(400, "Board Not Found"),
    METHOD_ARGUMENT_NOT_VALID(400, "MethodArgument Not Valid");

    private final int code;
    private final String message;

    ErrorStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
