package com.programmers.boardjpa.post.exception;

import lombok.Getter;

@Getter
public class PostException extends RuntimeException {
    private final PostErrorCode errorCode;

    public PostException(PostErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public PostException(PostErrorCode errorCode, Long id) {
        this.errorCode = errorCode;
        this.errorCode.addIdToMessage(id);
    }
}
