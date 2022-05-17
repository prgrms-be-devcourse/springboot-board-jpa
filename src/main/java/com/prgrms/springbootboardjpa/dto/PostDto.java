package com.prgrms.springbootboardjpa.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {

    private Long id;
    private String title;
    private String content;
    private UserDto author;
}
