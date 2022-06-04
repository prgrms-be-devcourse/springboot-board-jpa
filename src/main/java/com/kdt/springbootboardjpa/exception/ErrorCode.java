package com.kdt.springbootboardjpa.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {

    NOT_AUTHORIZED(401, "Operation Not Authorized"),
    ENTITY_NOT_FOUND(404, "ENTITY Not Found"),
    NOT_VALID(400, "Method Argument Not Valid"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");


    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
