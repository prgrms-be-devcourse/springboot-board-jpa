package com.example.demo.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String target) {
        super(target + "을 찾을 수 없습니다.");
    }
}
