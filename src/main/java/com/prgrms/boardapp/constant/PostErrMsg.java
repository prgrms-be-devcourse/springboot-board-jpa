package com.prgrms.boardapp.constant;

import static com.prgrms.boardapp.model.Post.TITLE_MAX_LENGTH;

public enum PostErrMsg {
    TITLE_LENGTH_ERR_MSG("제목의 최대길이는 " + TITLE_MAX_LENGTH +" 자 입니다.");

    String message;

    PostErrMsg(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}