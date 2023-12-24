package com.example.board.exception;

import com.example.board.dto.response.CustomResponseStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GeneralException extends RuntimeException {
    private CustomResponseStatus errorResponseStatus;

    public GeneralException(CustomResponseStatus errorResponseStatus) {
        super(errorResponseStatus.getMessage());
        this.errorResponseStatus = errorResponseStatus;
    }

    public CustomResponseStatus getErrorResponseStatus() {
        return errorResponseStatus;
    }
}
