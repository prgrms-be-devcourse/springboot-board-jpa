package com.example.board.auth.exception;

import com.example.board.dto.response.CustomResponseStatus;
import com.example.board.exception.GeneralException;

public class TokenException extends GeneralException {
    public TokenException(CustomResponseStatus errorResponseStatus) {
        super(errorResponseStatus);
    }
}
