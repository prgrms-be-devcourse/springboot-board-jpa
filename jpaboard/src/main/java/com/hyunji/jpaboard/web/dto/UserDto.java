package com.hyunji.jpaboard.web.dto;

import com.hyunji.jpaboard.domain.user.domain.User;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
public class UserDto {
    @NotBlank
    private String name;

    @Min(value = 1)
    private int age;

    @NotBlank
    private String hobby;

    public User toEntity() {
        return new User(this.name, this.age, this.hobby);
    }
}
