package com.prgrms.board.dto.user;

import com.prgrms.board.domain.User;
import com.prgrms.board.customValidation.Name;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;

@NoArgsConstructor
@Getter
public class UserCreateRequest {

    @Name
    private String name;

    @Positive
    private int age;

    private String hobby;

    private UserCreateRequest(String name, int age, String hobby){
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static UserCreateRequest from(String name, int age, String hobby){
        return new UserCreateRequest(name, age, hobby);
    }

    public User toEntity(){
        return User.builder()
                .name(name)
                .age(age)
                .hobby(hobby)
                .build();
    }

}