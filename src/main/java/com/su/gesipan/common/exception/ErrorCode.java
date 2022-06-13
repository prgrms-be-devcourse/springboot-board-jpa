package com.su.gesipan.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    INVALID_INPUT_VALUE(400, " Invalid Input Value"),

    METHOD_NOT_ALLOWED(405, " Method Not Allowed"),

    ENTITY_NOT_FOUND(400, " Entity Not Found"),

    INTERNAL_SERVER_ERROR(500, "Server Error");

    private int status;
    private final String message;
}