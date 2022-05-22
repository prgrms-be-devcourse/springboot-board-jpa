package me.prgms.board.post.dto;

import me.prgms.board.user.dto.UserDto;

public class ResponsePostDto {
    private Long id;
    private String title;
    private String content;
    private UserDto userDto;

    public ResponsePostDto(Long id, String title, String content, UserDto user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.userDto = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }
}
