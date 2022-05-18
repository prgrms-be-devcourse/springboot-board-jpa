package com.prgrms.hyuk.dto;

public class PostCreateRequest {

    private String title;
    private String content;

    private UserDto userDto;

    public PostCreateRequest(String title, String content, UserDto userDto) {
        this.title = title;
        this.content = content;
        this.userDto = userDto;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public UserDto getUserDto() {
        return userDto;
    }
}
