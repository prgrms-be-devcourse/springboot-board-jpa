package com.study.board.domain.exception;

import java.text.MessageFormat;

public class PostEditAccessDeniedException extends BoardRuntimeException {

    public PostEditAccessDeniedException(Long postId) {
        super(MessageFormat.format("you are not allowed to edit post of Id : {0}", postId));
    }
}
