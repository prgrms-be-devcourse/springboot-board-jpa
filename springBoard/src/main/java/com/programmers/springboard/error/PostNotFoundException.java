package com.programmers.springboard.error;

public class PostNotFoundException extends CustomException {
    public PostNotFoundException(ErrorCode postsNotFound) {
        super(postsNotFound);
    }
}
