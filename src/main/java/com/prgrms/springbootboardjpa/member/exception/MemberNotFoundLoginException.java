package com.prgrms.springbootboardjpa.member.exception;

public class MemberNotFoundLoginException extends MemberException {
    public MemberNotFoundLoginException() {
        this("입력하신 유저는 존재하지 않습니다.");
    }

    public MemberNotFoundLoginException(String message) {
        super(message);
    }
}
