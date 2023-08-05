package com.prgrms.board.global.exception.custom;

import org.springframework.dao.DataIntegrityViolationException;

public class DuplicateEmailException extends DataIntegrityViolationException {
    public DuplicateEmailException() {
        super("중복된 이메일이 존재합니다.");
    }
}