package com.example.board.exception;

import com.example.board.dto.response.CustomResponseStatus;

public class CustomException extends GeneralException {
    public CustomException(CustomResponseStatus errorResponseStatus) {
        super(errorResponseStatus);
    }
}
