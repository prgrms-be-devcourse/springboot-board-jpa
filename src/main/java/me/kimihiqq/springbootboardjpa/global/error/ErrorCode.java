package me.kimihiqq.springbootboardjpa.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    USER_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "User already exists"),
    INVALID_USER_INPUT(HttpStatus.BAD_REQUEST, "Invalid user input"),

    //Post
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "Post not found"),
    INVALID_POST_INPUT(HttpStatus.BAD_REQUEST, "Invalid post input");


    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}