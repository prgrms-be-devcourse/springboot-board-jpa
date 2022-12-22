package com.prgrms.springbootboardjpa.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException() {
        super("요청한 데이터를 찾을 수 없습니다.");
    }
}
