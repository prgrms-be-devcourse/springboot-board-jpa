package com.blackdog.springbootBoardJpa.global.response;

public enum SuccessCode {
    /**
     * Post - 게시글 관련 Code
     */
    POST_DELETE_SUCCESS("게시글이 성공적으로 삭제되었습니다."),

    /**
     * User - 유저 관련 성공 Code
     */
    USER_DELETE_SUCCESS("유저가 성공적으로 삭제되었습니다.");

    private final String message;

    SuccessCode(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
