package prgrms.project.post.controller.response;

import static org.springframework.http.HttpStatus.*;

public enum ErrorType {

    INVALID_VALUE_TYPE(BAD_REQUEST.value(), "Invalid Value Type"),

    ENTITY_NOT_FOUND(NOT_FOUND.value(),  "Entity Not Found"),

    SERVER_ERROR(INTERNAL_SERVER_ERROR.value(), "Got Server Error");

    private final int statusCode;
    private final String errorMessage;

    ErrorType(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public int getStatusCode() { return statusCode; }

    public String getErrorMessage() {
        return errorMessage;
    }
}
