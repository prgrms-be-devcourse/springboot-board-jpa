package com.example.springbootboardjpa.dto;

import com.example.springbootboardjpa.model.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostDTO {
    private String title;
    private String content;
    private UserDto userDto;

}
