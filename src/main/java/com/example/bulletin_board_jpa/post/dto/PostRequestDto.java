package com.example.bulletin_board_jpa.post.dto;


import com.example.bulletin_board_jpa.user.dto.UserDto;
import lombok.Getter;


@Getter
public class PostRequestDto {
    private String title;
    private String content;
    private UserDto userDto;

    public PostRequestDto(String title, String content, UserDto userDto) {
        this.title = title;
        this.content = content;
        this.userDto = userDto;
    }
}
