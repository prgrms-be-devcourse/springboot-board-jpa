package dev.jpaboard.post.exception;

import dev.jpaboard.common.exception.NotFoundException;

public class PostNotFoundException extends NotFoundException {
    private static final String MESSAGE = "게시글을 찾을 수 없습니다.";

    public PostNotFoundException() {
        super(MESSAGE);
    }

}
