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
    WRONG_USER_NAME(1001, HttpStatus.SERVICE_UNAVAILABLE, "사용자 이름이 존재하지 않습니다."),
    WRONG_USER_ID(1002, HttpStatus.SERVICE_UNAVAILABLE, "사용자 id 값이 존재하지 않습니다."),
    //2xxx post exception
    POST_NOT_FOUND(2000, HttpStatus.NOT_FOUND, "찾으시는 게시글이 없습니다."),
    WRITER_NOT_MATCHED(2001, HttpStatus.BAD_REQUEST, "작성자가 일치하지 않습니다."),
    WRONG_TITLE_VALUE(2002, HttpStatus.BAD_REQUEST, "게시글 제목은 1자~20자 이내로 작성해주십시오."),
    WRONG_CONTENTS_VALUE(2003, HttpStatus.BAD_REQUEST, "게시글 내용을 작성해주십시오.");

    private int code;
    private HttpStatus httpStatus;
    private String message;
}
