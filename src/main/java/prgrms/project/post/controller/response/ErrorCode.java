package prgrms.project.post.controller.response;

import lombok.Getter;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(BAD_REQUEST.value(), "Entered invalid value"),

    NOT_SUPPORTED_MEDIA_TYPE(UNSUPPORTED_MEDIA_TYPE.value(), "This media type is not supported"),

    TYPE_MISMATCH(BAD_REQUEST.value(), "Entered wrong type"),

    ENTITY_NOT_FOUND(NOT_FOUND.value(), "Couldn't find required entity"),

    SERVER_ERROR(INTERNAL_SERVER_ERROR.value(), "Unknown server error");

    private final int statusCode;

    private final String errorMessage;

    ErrorCode(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }
}
