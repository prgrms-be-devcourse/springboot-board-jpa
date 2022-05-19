package com.example.spring_jpa_post.post.exception;

import com.example.spring_jpa_post.global.exception.EntityNotFoundException;

public class PostNotFoundException extends EntityNotFoundException {
    public PostNotFoundException(Long target) {
        super(target + " is not found");
    }
}
