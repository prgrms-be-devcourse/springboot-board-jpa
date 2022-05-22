package me.prgms.board.post.dto;

import me.prgms.board.user.dto.UserDto;

public class CreatePostDto {
    private String title;
    private String content;
    private UserDto userDto;

    public CreatePostDto() {}

    public CreatePostDto(String title, String content, UserDto userDto) {
        this.title = title;
        this.content = content;
        this.userDto = userDto;
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

    public void setUser(UserDto userDto) {
        this.userDto = userDto;
    }
}
