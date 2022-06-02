package com.prgrms.springboard.post.exception;

import com.prgrms.springboard.global.error.exception.NotFoundException;

public class PostNotFoundException extends NotFoundException {
    public PostNotFoundException(Long id) {
        super("ID가 %d인 게시글은 없습니다.".formatted(id));
    }
}
