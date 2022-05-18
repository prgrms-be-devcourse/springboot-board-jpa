package com.prgrms.board.user.dto;

import com.prgrms.board.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class UserResponse {
    Long userId;
    String name;
    Long age;
    String hobby;
    LocalDateTime createdAt;

    public static UserResponse of(User user) {
        return UserResponse.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
