package com.devcourse.springbootboardjpa.common.dto;

import com.devcourse.springbootboardjpa.common.exception.ErrorCode;

public class ErrorResponse extends ResponseDTO {

    public ErrorResponse(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }
}
