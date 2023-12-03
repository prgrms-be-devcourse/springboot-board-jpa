package com.example.board.exception;

import com.example.board.dto.response.ResponseStatus;

public class CustomException extends GeneralException {
    public CustomException(ResponseStatus errorResponseStatus) {
        super(errorResponseStatus);
    }
}
