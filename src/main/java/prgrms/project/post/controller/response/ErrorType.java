package prgrms.project.post.controller.response;

public enum ErrorType {

    INVALID_VALUE_TYPE("Invalid Value Type"),

    ENTITY_NOT_FOUND( "Entity Not Found"),

    SERVER_ERROR("Got Server Error");

    private final String errorMessage;

    ErrorType(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
