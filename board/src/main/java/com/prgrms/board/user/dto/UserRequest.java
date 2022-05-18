package com.prgrms.board.user.dto;

import com.prgrms.board.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRequest {
    private String name;
    private Long age;
    private String hobby;


    public static User toUser(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.name)
                .age(userRequest.age)
                .hobby(userRequest.hobby)
                .build();
    }
}