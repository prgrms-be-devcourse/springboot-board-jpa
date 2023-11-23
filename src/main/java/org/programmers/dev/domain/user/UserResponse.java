package org.programmers.dev.domain.user;

import lombok.Builder;
import lombok.Data;
import org.programmers.dev.domain.user.domain.entity.User;

@Data
public class UserResponse {

    private Long id;
    private String name;
    private Integer age;
    private String hobby;

    @Builder
    public UserResponse(Long id, String name, Integer age, String hobby) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
            .id(user.getId())
            .name(user.getName())
            .age(user.getAge())
            .hobby(user.getHobby())
            .build();
    }
}
