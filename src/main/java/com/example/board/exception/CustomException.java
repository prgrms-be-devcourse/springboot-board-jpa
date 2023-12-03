package com.example.board.exception;

public class CustomException extends GeneralException {
    public CustomException(CustomError customError) {
        super(customError);
    }
}
