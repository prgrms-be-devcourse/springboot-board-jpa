package com.programmers.boardjpa.post.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum PostErrorCode {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당하는 Post를 찾을 수 없습니다.");

    private final HttpStatus errorCode;
    private final String errorMessage;

    PostErrorCode(HttpStatus errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
