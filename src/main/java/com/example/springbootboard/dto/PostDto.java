package com.example.springbootboard.dto;

import com.example.springbootboard.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private UserDto userDto;

    @Builder
    public PostDto(Long id, String title, String content, UserDto userDto){
        this.id = id;
        this.title = title;
        this.content = content;
        this.userDto = userDto;
    }
}
