package com.example.springbootboardjpa.dto.user.response;

import com.example.springbootboardjpa.entity.User;
import com.example.springbootboardjpa.enums.Hobby;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class UserRepsonse {
    private Long userId;
    private String name;
    private int age;
    private Hobby hobby;
    private LocalDateTime createdAt;
    private String createdBy;

    @Builder
    public UserRepsonse(Long userId, String name, int age, Hobby hobby, LocalDateTime createdAt, String createdBy) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    public static UserRepsonse fromEntity(User user) {
        return UserRepsonse.builder()
                .userId(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
