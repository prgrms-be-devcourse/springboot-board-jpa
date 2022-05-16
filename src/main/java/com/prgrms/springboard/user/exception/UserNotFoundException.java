package com.prgrms.springboard.user.exception;

import com.prgrms.springboard.global.error.exception.NotFoundException;

public class UserNotFoundException extends NotFoundException {
    public UserNotFoundException(Long id) {
        super("ID가 %d인 회원은 없습니다.".formatted(id));
    }
}
