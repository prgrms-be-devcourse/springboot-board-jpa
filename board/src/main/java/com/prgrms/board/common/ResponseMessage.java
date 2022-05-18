package com.prgrms.board.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ResponseMessage {
    SUCCESS(200, "성공"),
    NOT_FOUND_EXCEPTION(400, "찾을 수 없습니다."),
    MAX_POST_EXCEPTION(406, "글은 하루 최대 3개만 등록할 수 있습니다.");

    private final int code;
    private final String msg;
}
