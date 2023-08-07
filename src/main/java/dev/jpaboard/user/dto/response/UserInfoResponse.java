package dev.jpaboard.user.dto.response;

import dev.jpaboard.user.domain.User;
import lombok.Builder;

@Builder
public record UserInfoResponse(String email, String name, int age, String hobby) {

    public static UserInfoResponse from(User user) {
        return UserInfoResponse.builder()
                .email(user.getEmail())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

}
