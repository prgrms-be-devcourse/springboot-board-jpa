package com.homework.springbootboard.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private Long id;
    private String name;
    private int age;
    private String hobby;
    private List<PostDto> postDtos;

    @Builder
    public UserDto(Long id, String name, int age, String hobby, List<PostDto> postDtos) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.postDtos = postDtos;
    }
}
