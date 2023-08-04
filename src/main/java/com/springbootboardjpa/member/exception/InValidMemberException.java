package com.springbootboardjpa.member.exception;

public class InValidMemberException extends RuntimeException {
    public InValidMemberException(String message) {
        super(message);
    }

    public InValidMemberException() {
        this("유효하지 않은 회원입니다.");
    }
}
