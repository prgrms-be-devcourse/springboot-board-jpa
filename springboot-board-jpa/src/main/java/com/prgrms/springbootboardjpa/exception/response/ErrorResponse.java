package com.prgrms.springbootboardjpa.exception.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {
    private HttpStatus statusCode;
    private String errorDetails;

    public ErrorResponse(HttpStatus statusCode, String errorDetails){
        this.statusCode = statusCode;
        this.errorDetails = errorDetails;
    }

    public static ErrorResponse IllegalArgumentException(String errorDetails){
        return new ErrorResponse(HttpStatus.NOT_ACCEPTABLE, errorDetails);
    }

    public static ErrorResponse customRuntimeException(HttpStatus statusCode, String errorDetails){
        return new ErrorResponse(statusCode,errorDetails);
    }
}
