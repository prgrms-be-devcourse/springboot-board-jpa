package com.prgrms.springbootboardjpa.user.dto;

import com.prgrms.springbootboardjpa.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserResponse {
    private final Long id;
    private final String nickName;
    private final int age;
    private final String hobby;
    private final String firstName;
    private final String lastName;
    private final String email;

    public static UserResponse convertToUserResponse(User user){
        return new UserResponse(user.getId(), user.getNickName(), user.getAge(), user.getHobby(), user.getName().getFirstName(), user.getName().getLastName(), user.getEmail());
    }
}
