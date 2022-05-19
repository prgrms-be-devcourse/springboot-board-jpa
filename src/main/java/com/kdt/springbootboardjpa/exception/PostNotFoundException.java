package com.kdt.springbootboardjpa.exception;

public class PostNotFoundException extends IllegalArgumentException{
    public PostNotFoundException(String message){
        super(message);
    }
}
