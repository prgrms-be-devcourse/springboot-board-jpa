package org.programmers.springboardjpa.domain.post.exception;

import org.programmers.springboardjpa.global.error.exception.BusinessException;
import org.programmers.springboardjpa.global.error.exception.ErrorCode;

public class NoExistIdException extends BusinessException {
    public NoExistIdException() {
        super(ErrorCode.POST_NOT_FOUND.getMessage() , ErrorCode.POST_NOT_FOUND);
    }
}
