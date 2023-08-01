package com.programmers.springbootboardjpa.global.exception.rule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum UserRule implements BusinessRule {

    USER_NOT_FOUND_FOR_FIND(HttpStatus.BAD_REQUEST, "찾는 유저가 없습니다."),
    USER_NOT_FOUND_FOR_DELETE(HttpStatus.BAD_REQUEST, "삭제하려는 유저가 없습니다."),
    USER_NOT_FOUND_FOR_UPDATE(HttpStatus.BAD_REQUEST, "업데이트하려는 유저가 없습니다.");

    private final HttpStatus status;
    private final String message;

}
