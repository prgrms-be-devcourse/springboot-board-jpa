package com.prgrms.hyuk.dto;

public class PostDto {

    private long id;
    private String title;
    private String content;

    private UserDto user;

    public PostDto(long id, String title, String content, UserDto user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.user = user;
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

    public UserDto getUser() {
        return user;
    }
}
