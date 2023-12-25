package com.example.board.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneralException extends RuntimeException {
    private CustomError customError;

    public GeneralException(CustomError customError) {
        super(customError.getMessage());
        this.customError = customError;
    }

    public CustomError getCustomError() {
        return customError;
    }
}
