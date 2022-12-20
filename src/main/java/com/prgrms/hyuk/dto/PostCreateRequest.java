package com.prgrms.hyuk.dto;

public class PostCreateRequest {

    private String title;
    private String content;

    private UserDto user;

    public PostCreateRequest(String title, String content, UserDto user) {
        this.title = title;
        this.content = content;
        this.user = user;
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
