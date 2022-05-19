package com.example.board.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class UserDto {
    private Long id;
    private String name;
    private int age;
    private String hobby;
    private List<PostDto> postDtos;
}
