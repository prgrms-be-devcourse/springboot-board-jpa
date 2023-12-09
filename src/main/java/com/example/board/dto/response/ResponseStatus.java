package com.example.board.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResponseStatus {
    // 200 ok
    OK(HttpStatus.OK, 20000, "요청 성공"),
    TOKEN_REGENERATED(HttpStatus.OK, 20001, "토큰이 재발급 되었습니다."),

    // 400 bad request
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 40000, "잘못된 요청입니다."),
    // Token 40001 ~ 40009
    MALFORMED_TOKEN(HttpStatus.BAD_REQUEST, 40001, "형식이 잘못된 토큰입니다."),
    UNSUPPORTED_TOKEN(HttpStatus.BAD_REQUEST, 40002, "지원되지 않는 형식의 토큰입니다."),
    EMPTY_CLAIMS_TOKEN(HttpStatus.BAD_REQUEST, 40003, "토큰에 클레임이 없습니다."),
    // User 40110 ~ 40119
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, 40010, "입력값이 올바르지 않습니다."),
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, 40011, "잘못된 비밀번호입니다."),
    PASSWORD_CONFIRM_NOT_MATCHED(HttpStatus.BAD_REQUEST, 40012, "비밀번호와 비밀번호 확인이 일치하지 않습니다."),

    // 401 unauthorized
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 40100, "인증되지 않은 사용자입니다."),
    // Token 40101 ~ 40109
    INVALID_SIGNATURE_TOKEN(HttpStatus.UNAUTHORIZED, 40101, "유효하지 않은 토큰 서명입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 40102, "만료된 토큰입니다."),

    // 403 forbidden
    FORBIDDEN(HttpStatus.FORBIDDEN, 40300, "접근 권한이 없습니다."),
    // Token 40301 ~ 40309
    MISSING_AUTHORIZATION_CLAIM(HttpStatus.FORBIDDEN, 40301, "토큰에 권한 정보가 누락되었습니다."),

    // 404 not found
    NOT_FOUND(HttpStatus.NOT_FOUND, 40400, "요청을 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_FOUND(HttpStatus.NOT_FOUND, 40401, "리프레시 토큰을 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 40410, "유저를 찾을 수 없습니다."),
    ALREADY_DELETED_USER(HttpStatus.BAD_REQUEST, 40411, "이미 탈퇴한 유저입니다."),
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, 40412, "게시글을 찾을 수 없습니다."),

    // 409 conflict
    DUPLICATED_USER_EMAIL(HttpStatus.CONFLICT, 40901, "이미 존재하는 이메일입니다."),
    AUTHOR_NOT_MATCH(HttpStatus.CONFLICT, 40902, "작성자가 일치하지 않습니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50000, "서버 에러. 관리자에게 문의하세요."),
    TOKEN_PROCESSING_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 50001, "토큰 처리 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;

    ResponseStatus(HttpStatus httpStatus, Integer code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
