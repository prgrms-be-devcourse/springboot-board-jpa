package com.example.spring_jpa_post.user.exception;

import com.example.spring_jpa_post.global.exception.EntityNotFoundException;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(Long target) {
        super(target + " is not found");
    }
}
