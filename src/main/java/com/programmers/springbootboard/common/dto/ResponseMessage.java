package com.programmers.springbootboard.common.dto;

import org.springframework.http.HttpStatus;

public enum ResponseMessage {
    MEMBER_SIGN_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
    MEMBER_DELETE_SUCCESS(HttpStatus.OK, "회원삭제 성공"),
    MEMBER_UPDATE_SUCCESS(HttpStatus.CREATED, "회원수정 성공"),
    MEMBER_INQUIRY_SUCCESS(HttpStatus.OK, "회원조회 성공"),
    POST_INSERT_SUCCESS(HttpStatus.CREATED, "게시글 삽입 성공"),
    POST_UPDATE_SUCCESS(HttpStatus.OK, "게시글 수정 성공"),
    POST_INQUIRY_SUCCESS(HttpStatus.OK, "게시글 조회 성공"),
    POSTS_INQUIRY_SUCCESS(HttpStatus.OK, "전체 게시글 조회 성공"),
    POST_DELETE_SUCCESS(HttpStatus.OK, "게시글 삭제 성공");


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
