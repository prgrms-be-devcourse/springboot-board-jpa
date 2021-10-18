package com.programmers.springbootboard.common.dto;

import org.springframework.http.HttpStatus;

public enum ResponseMessage {
    MEMBER_LOGIN_SUCCESS("로그인 성공"),
    MEMBER_SIGN_SUCCESS("회원가입 성공"),
    MEMBER_DELETE_SUCCESS("회원삭제 성공"),
    MEMBER_UPDATE_SUCCESS("회원수정 성공"),
    MEMBER_INQUIRY_SUCCESS("회원조회 성공"),
    MEMBERS_INQUIRY_SUCCESS("전체 회원조회 성공"),
    POST_INSERT_SUCCESS("게시글 삽입 성공"),
    POST_UPDATE_SUCCESS("게시글 수정 성공"),
    POST_INQUIRY_SUCCESS("게시글 조회 성공"),
    POSTS_INQUIRY_SUCCESS("전체 게시글 조회 성공"),
    POST_DELETE_SUCCESS("게시글 삭제 성공");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
