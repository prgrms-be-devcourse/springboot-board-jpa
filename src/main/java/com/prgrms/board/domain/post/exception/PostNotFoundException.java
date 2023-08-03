package com.prgrms.board.domain.post.exception;

import com.prgrms.board.global.common.ErrorCode;
import com.prgrms.board.global.exception.CustomException;

public class PostNotFoundException extends CustomException {
    
    public PostNotFoundException(ErrorCode error) {
        super(error);
    }
}
