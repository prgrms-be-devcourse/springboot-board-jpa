package com.prgrms.springbootboardjpa.post.exception;

public class NotFoundByIdPostException extends PostException {


    public NotFoundByIdPostException(Long postId) {
        super(postId + "에 해당하는 post를 찾을 수 없습니다.");
    }
}
