package com.prgrms.springbootboardjpa.post.exception;

public class PostNotAuthorizationException extends PostException {
    public PostNotAuthorizationException(Long idFromSession, Long postId) {
        this(idFromSession + "의 회원은 " + postId + "에 권한이 없습니다.");
    }

    public PostNotAuthorizationException(String message) {
        super(message);
    }
}
