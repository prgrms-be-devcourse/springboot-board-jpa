package prgrms.project.post.controller.response;

import lombok.Getter;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorType {

    INVALID_VALUE_TYPE(BAD_REQUEST.name(), BAD_REQUEST.value(), "Invalid Value Type"),

    ENTITY_NOT_FOUND(NOT_FOUND.name(), NOT_FOUND.value(), "Entity Not Found"),

    SERVER_ERROR(INTERNAL_SERVER_ERROR.name(), INTERNAL_SERVER_ERROR.value(), "Got Server Error");

    private final String error;
    private final int statusCode;
    private final String errorMessage;

    ErrorType(String error, int statusCode, String errorMessage) {
        this.error = error;
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }
}
