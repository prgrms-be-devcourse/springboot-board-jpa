package com.example.board.exception;

import com.example.board.dto.response.ResponseStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneralException extends RuntimeException {
    private ResponseStatus errorResponseStatus;

    public GeneralException(ResponseStatus errorResponseStatus) {
        super(errorResponseStatus.getMessage());
        this.errorResponseStatus = errorResponseStatus;
    }

    public ResponseStatus getErrorResponseStatus() {
        return errorResponseStatus;
    }
}
