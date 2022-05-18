package com.prgrms.hyuk.dto;

public class PostDto {

    private long id;
    private String title;
    private String content;

    private String userName;

    public PostDto(long id, String title, String content, String userName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userName = userName;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getUserName() {
        return userName;
    }
}
