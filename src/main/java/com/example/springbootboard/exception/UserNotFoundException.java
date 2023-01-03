package com.example.springbootboard.exception;

public class UserNotFoundException extends Exception{
    @Override
    public String getMessage() {
        return "User not found please check name\n";
    }
}
