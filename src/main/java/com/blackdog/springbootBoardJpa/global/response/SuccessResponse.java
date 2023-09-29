package com.blackdog.springbootBoardJpa.global.response;

public class SuccessResponse {
    private String message;

    private SuccessResponse(String message) {
        this.message = message;
    }

    public static SuccessResponse of(SuccessCode code) {
        return new SuccessResponse(code.getMessage());
    }

    public String getMessage() {
        return message;
    }
}
