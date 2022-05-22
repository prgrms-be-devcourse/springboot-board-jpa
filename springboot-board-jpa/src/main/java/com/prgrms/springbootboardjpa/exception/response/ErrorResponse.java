package com.prgrms.springbootboardjpa.exception.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private int statusCode;
    private String errorDetails;

    public ErrorResponse(int statusCode, String errorDetails){
        this.statusCode = statusCode;
        this.errorDetails = errorDetails;
    }

    public static ErrorResponse IllegalArgumentException(String errorDetails){
        return new ErrorResponse(HttpStatus.NOT_ACCEPTABLE.value(), errorDetails);
    }

    public static ErrorResponse customRuntimeException(int statusCode, String errorDetails){
        return new ErrorResponse(statusCode,errorDetails);
    }
}
