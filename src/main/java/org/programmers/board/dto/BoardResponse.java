package org.programmers.board.dto;

public class BoardResponse {

    private Long id;
    private String title;
    private String content;
    private String userName;

    public BoardResponse(Long id, String title, String content, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}