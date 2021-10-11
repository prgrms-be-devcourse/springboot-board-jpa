package com.homework.springbootboard.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorStatus {
    METHOD_ARGUMENT_NOT_VALID(HttpStatus.BAD_REQUEST,"Method argument is not valid"),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND,"Post is not found"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"Internal server error");


    private final HttpStatus status;
    private final String message;

    ErrorStatus(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
