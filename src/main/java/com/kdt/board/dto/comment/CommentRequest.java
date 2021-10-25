package com.kdt.board.dto.comment;

public class CommentRequest {
    private String content;
    private String userName;

    public CommentRequest(String content, String userName) {
        this.content = content;
        this.userName = userName;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getContent() { return this.content; }

}
