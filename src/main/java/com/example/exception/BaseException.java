package com.example.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BaseException extends RuntimeException{
    private int code;
    private String message;
    private HttpStatus httpStatus;

    public BaseException(ErrorMessage message){
        this.code = message.getCode();
        this.message = message.getMessage();
        this.httpStatus = message.getHttpStatus();
    }

}
