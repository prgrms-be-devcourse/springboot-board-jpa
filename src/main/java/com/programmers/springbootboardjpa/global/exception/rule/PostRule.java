package com.programmers.springbootboardjpa.global.exception.rule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum PostRule implements BusinessRule {

    POST_NOT_FOUND_FOR_FIND(HttpStatus.NOT_FOUND, "찾는 게시글이 없습니다."),
    POST_NOT_FOUND_FOR_DELETE(HttpStatus.NOT_FOUND, "삭제하려는 게시글이 없습니다."),
    POST_NOT_FOUND_FOR_UPDATE(HttpStatus.NOT_FOUND, "업데이트하려는 게시글이 없습니다.");

    private final HttpStatus status;
    private final String message;

}
