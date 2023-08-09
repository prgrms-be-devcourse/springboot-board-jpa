package com.jpaboard.global.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeWithDetail {
    // Common
    ENTITY_NOT_FOUND(404, "C001", "해당 엔티티를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "C999", "서버 내부 에러입니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCodeWithDetail(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

}
