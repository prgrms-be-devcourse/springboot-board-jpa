package com.programmers.springbootboardjpa.domain.user.dto;

import com.programmers.springbootboardjpa.domain.user.domain.User;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record UserResponseDto(Long id, String name, int age, String hobby, String createdBy, LocalDateTime createdAt) {

    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .build();
    }
}
