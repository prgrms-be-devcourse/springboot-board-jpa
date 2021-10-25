package com.prgrms.board.post.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {
    private Long id;

    @NotBlank(message = "enter the name.")
    @Length(max = 30)
    private String name;

    @NotBlank(message = "enter the age.")
    private int age;

    private String hobby;

    @Builder
    public UserDto(Long id, String name, int age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }
}

