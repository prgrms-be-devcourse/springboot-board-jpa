package com.example.springbootboard.exception;

public class PostNotFoundException extends Exception{
    @Override
    public String getMessage() {
        return "Post not found please check id\n";
    }
}
