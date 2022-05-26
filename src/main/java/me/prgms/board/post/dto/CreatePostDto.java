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

    public String getContent() {
        return content;
    }

    public UserDto getUserDto() {
        return userDto;
    }

}
