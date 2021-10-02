package com.programmers.springbootboard.common.dto;

import org.springframework.http.HttpStatus;

public enum ResponseMessage {
    SIGN_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
    DELETE_SUCCESS(HttpStatus.OK, "회원삭제 성공"),
    UPDATE_SUCCESS(HttpStatus.CREATED, "회원수정 성공"),
    INQUIRY_MEMBER_SUCCESS(HttpStatus.OK, "회원조회 성공");


    private final HttpStatus status;
    private final String message;

    ResponseMessage(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus status() {
        return status;
    }
}
