package com.prgrms.board.dto.user;

import com.prgrms.board.domain.Users;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Getter
public class UserResponse {

    private final Long userId;
    private final String email;
    private final String name;
    private final int age;

    public static UserResponse fromEntity(Users user) {
        return new UserResponse(
                user.getUserId(),
                user.getEmail(),
                user.getName(),
                user.getAge()
        );
    }
}
