package com.example.board.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {

    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "중복된 이메일입니다."),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "회원이 존재하지 않습니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "게시물이 존재하지 않습니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."),
    WRITER_MISMATCH(HttpStatus.BAD_REQUEST, "게시글 작성자가 일치하지 않습니다."),
    POST_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "게시글 수정에 실패했습니다."),
    MEMBER_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "회원 수정에 실패했습니다."),
    PARENT_COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "부모 댓글을 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다"),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "권한을 찾을 수 없습니다."),
    ILLEGAL_AUTHENTICATION(HttpStatus.UNAUTHORIZED, "잘못된 인증 요청입니다."),
    AUTHENTICATION_ENTRY_POINT(HttpStatus.UNAUTHORIZED, "로그인 후 재시도 해주세요."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "접근 권한이 없습니다."),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "유효하지 않은 토큰입니다. 로그인이 필요합니다."),
    LOGIN_FAILED(HttpStatus.CONFLICT,"로그인에 실패하였습니다. 아이디나 비밀번호를 확인해주세요."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "재로그인이 필요합니다."),
    UNSUPPORTED_JWT(HttpStatus.BAD_REQUEST, "유효하지 않은 인증 정보입니다."),
    ALREADY_AUTH_KEY_EXIST(HttpStatus.CONFLICT, "이미 존재하는 이메일 인증 키 입니다."),
    VALIDATE_EMAIL_FAILED(HttpStatus.BAD_REQUEST, "인증키가 일치하지 않습니다."),
    NOT_EQUALS_RESET_PASSWORD(HttpStatus.BAD_REQUEST, "재설정하려는 비밀번호가 일치하지 않습니다."),
    MESSAGING_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 메시지 생성에 실패했습니다."),
    UNSUPPORTED_ENCODING(HttpStatus.BAD_REQUEST, "이메일 인증키 전송 중 인코딩을 지원하지 않는 오류가 발생했습니다"),
    SEND_MAIL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "이메일 인증키 전송에 실패했습니다 다시 시도해주세요."),
    REFRESH_TOKEN_NOT_EXIST(HttpStatus.NOT_FOUND, "Refresh 토큰이 존재하지 않습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public static ErrorCode defaultError() {
        return INTERNAL_SERVER_ERROR;
    }
}
