package com.devcourse.springbootboardjpa.common.dto;

import com.devcourse.springbootboardjpa.common.exception.ErrorCode;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;


public class ErrorResponse extends ResponseDTO {

    private static final Integer CONFLICT_STATUS = 409;

    public ErrorResponse(ErrorCode errorCode) {
        super(errorCode.getStatus(), errorCode.getMessage());
    }

    public ErrorResponse(BindException bindException) {
        super(CONFLICT_STATUS, getBindingErrorMessages(bindException));
    }

    public ErrorResponse(RuntimeException e) {
        super(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    private static String getBindingErrorMessages(BindException bindException) {
        return bindException.getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce("", (beforeErrorMessages, errorMessage) -> beforeErrorMessages + errorMessage + ", ");
    }
}
