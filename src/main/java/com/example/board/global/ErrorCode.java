package com.example.board.global;

public enum ErrorCode {
    // Post
    POST_NOT_FOUND(404, "P001", "Post Not Founded"),

    // Global
    INVALID_INPUT_VALUE(400, "G001", "Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "G002", "Method Not Allowed"),
    INTERNAL_SERVER_ERROR(504, "G003", "Internal Server Error");

    private final int code;
    private final String uniqueMessage;
    private final String errorMessage;

    ErrorCode(int code, String uniqueMessage, String errorMessage) {
        this.code = code;
        this.uniqueMessage = uniqueMessage;
        this.errorMessage = errorMessage;
    }

    public String getUniqueMessage() {
        return uniqueMessage;
    }
}
