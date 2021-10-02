package kr.ac.hs.oing.common.dto;

import org.springframework.http.HttpStatus;

public enum ResponseMessage {
    SIGN_SUCCESS(HttpStatus.CREATED, "회원가입 성공"),
    LOGIN_SUCCESS(HttpStatus.OK, "로그인 성공"),
    CREATE_CLUB_SUCCESS(HttpStatus.CREATED, "클럽 생성 성공");

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
