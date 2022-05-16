package com.prgrms.springboard.post.exception;

import com.prgrms.springboard.global.error.exception.InvalidValueException;

public class UserNotHavePermission extends InvalidValueException {
    public UserNotHavePermission(Long id) {
        super("ID가 %d인 회원은 해당 글을 수정할 권한이 없습니다.".formatted(id));
    }
}
