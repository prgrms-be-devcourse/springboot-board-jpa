package com.prgrms.boardjpa.exception;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message){
        super(message);
    }
}
