package com.prgms.jpaBoard.domain.user.application.dto;

import com.prgms.jpaBoard.domain.user.HobbyType;
import com.prgms.jpaBoard.domain.user.User;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record UserResponse(
        Long id,
        String name,
        int age,
        HobbyType hobby,
        LocalDateTime createdAt
) {

    public UserResponse(User user) {
        this(user.getId(), user.getName(), user.getAge(), user.getHobby(), user.getCreatedAt());
    }
}
