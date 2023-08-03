package com.prgrms.board.global.common;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ErrorCode {
    
    NO_USER(NOT_FOUND, "존재하지 않는 유저입니다."),
    NO_POST(NOT_FOUND, "존재하지 않는 게시물입니다."),
    ;

    private final HttpStatus status;
    private final String message;

    public int getStatusCode() {
        return status.value();
    }
}
