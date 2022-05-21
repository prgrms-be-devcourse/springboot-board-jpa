package com.study.board.exception;

import java.text.MessageFormat;

public class BoardNotFoundException extends BoardRuntimeException{

    public BoardNotFoundException(Class<?> clazz, Long id) {
        super(MessageFormat.format("존재하지 않는 {0}에 대한 조회 요청 (요청 id :{1}) " +
                ". 필터 로직 확인 필요", clazz.getSimpleName(), id));
    }
}
