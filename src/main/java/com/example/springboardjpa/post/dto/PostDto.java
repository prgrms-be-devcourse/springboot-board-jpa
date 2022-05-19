package com.example.springboardjpa.post.dto;

import com.example.springboardjpa.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private long id;
    private String title;
    private String content;
    private UserDto userDto;
}
