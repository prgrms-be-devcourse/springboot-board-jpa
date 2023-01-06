package com.prgrms.springbootboardjpa.member.exception;

public class MemberNotFoundByIdException extends MemberException {

    public MemberNotFoundByIdException(Long memberId) {
        this(memberId + "에 해당하는 member를 찾지 못하였습니다.");
    }

    public MemberNotFoundByIdException(String message) {
        super(message);
    }
}
