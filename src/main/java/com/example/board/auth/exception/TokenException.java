package com.example.board.auth.exception;

import com.example.board.dto.response.ResponseStatus;
import com.example.board.exception.GeneralException;

public class TokenException extends GeneralException {
    public TokenException(ResponseStatus errorResponseStatus) {
        super(errorResponseStatus);
    }
}
