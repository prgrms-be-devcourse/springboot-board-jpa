package com.programmers.jpaboard.member.controller.status;

import lombok.Getter;

@Getter
public enum MemberResponseStatus {
    MEMBER_CREATION_SUCCESS("Member Creation Success");

    private String message;

    MemberResponseStatus(String message) {
        this.message = message;
    }
}