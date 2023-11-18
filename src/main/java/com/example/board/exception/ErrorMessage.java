package com.example.board.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorMessage {
    INTERNAL_SERVER_ERROR(500, HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부에서 장애가 발생했습니다."),
    // 1xxx user exception
    USER_NOT_FOUND(1000, HttpStatus.NOT_FOUND, "찾으시는 사용자가 없습니다."),

    //2xxx post exception
    POST_NOT_FOUND(2000, HttpStatus.NOT_FOUND, "찾으시는 게시글이 없습니다.");

    private int code;
    private HttpStatus httpStatus;
    private String message;
}
