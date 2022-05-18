package com.programmers.board.common.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorResponse {

    private int status;

    private String message;

    private ErrorResponse(ErrorCode code){
        this.status = code.getStatus();
        this.message = code.getMessage();
    }

    public static ErrorResponse of(ErrorCode code){
        return new ErrorResponse(code);
    }
}
