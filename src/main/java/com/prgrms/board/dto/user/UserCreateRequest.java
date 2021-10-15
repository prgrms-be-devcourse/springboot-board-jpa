package com.prgrms.board.dto.user;

import com.prgrms.board.domain.User;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Getter
public class UserCreateRequest {

    @NotBlank(message = "이름이 비어있습니다.")
    private String name;

    @Positive
    private int age;

    private String hobby;

    public UserCreateRequest(){
    }

    public UserCreateRequest(String name, int age, String hobby){
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }

}