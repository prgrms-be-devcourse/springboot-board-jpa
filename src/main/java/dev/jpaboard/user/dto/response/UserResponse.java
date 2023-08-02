package dev.jpaboard.user.dto.response;

import dev.jpaboard.user.domain.User;
import lombok.Builder;

@Builder
public record UserResponse(Long id, String email, String name, int age, String hobby) {

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

}
