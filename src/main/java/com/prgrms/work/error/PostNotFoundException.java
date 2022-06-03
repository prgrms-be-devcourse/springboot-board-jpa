package com.prgrms.work.error;

public class PostNotFoundException extends RuntimeException{

    public PostNotFoundException() {
        super("해당 게시글을 조회할 수 없습니다.");
    }

}
