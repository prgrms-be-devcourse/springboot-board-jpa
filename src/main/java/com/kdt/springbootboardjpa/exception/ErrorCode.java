package com.kdt.springbootboardjpa.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_AUTHORIZED(401, "Operation Not Authorized"),
    ENTITY_NOT_FOUND(404, "ENTITY Not Found");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
