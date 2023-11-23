package com.devcourse.springbootboardjpahi.dto;

import com.devcourse.springbootboardjpahi.domain.User;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String name,
        Integer age,
        String hobby,
        LocalDateTime createdAt
) {

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
