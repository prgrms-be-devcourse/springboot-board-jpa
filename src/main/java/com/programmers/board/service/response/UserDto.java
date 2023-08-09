package com.programmers.board.service.response;

import com.programmers.board.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDto {
    private final Long userId;
    private final String name;
    private final int age;
    private final String hobby;

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getName(), user.getAge(), user.getHobby());
    }
}
