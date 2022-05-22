package prgrms.project.post.controller.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorType {

    INVALID_VALUE_TYPE(BAD_REQUEST.name(), "Invalid Value Type"),

    ENTITY_NOT_FOUND(NOT_FOUND.name(), "Entity Not Found"),

    SERVER_ERROR(INTERNAL_SERVER_ERROR.name(), "Got Server Error");

    private final String error;
    private final String errorMessage;

    ErrorType(String error, String errorMessage) {
        this.error = error;
        this.errorMessage = errorMessage;
    }
}
