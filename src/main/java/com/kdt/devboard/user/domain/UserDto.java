package com.kdt.devboard.user.domain;

import com.kdt.devboard.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDto {

    private Long id;
    private String name;
    private int age;
    private String hobby;
    private LocalDateTime createAt;
    private String createBy;

    public UserDto(User entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.age = entity.getAge();
        this.hobby = entity.getHobby();
        this.createAt = entity.getCreateAt();
        this.createBy = entity.getCreateBy();
    }

    public User toEntity() {
        return User.builder()
                .id(this.id)
                .name(this.name)
                .age(this.age)
                .hobby(this.hobby)
                .createAt(this.createAt)
                .createBy(this.createBy)
                .build();
    }

}
