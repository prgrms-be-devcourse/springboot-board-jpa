package com.prgrms.board.post.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class UserDto {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private int age;

    private String hobby;

    protected UserDto() {
    }

    @Builder
    public UserDto(Long id, String name, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}
