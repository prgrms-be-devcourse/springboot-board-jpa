package com.prgrms.springboard.user.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.prgrms.springboard.post.domain.Post;
import com.prgrms.springboard.user.domain.User;
import com.prgrms.springboard.user.domain.vo.Age;
import com.prgrms.springboard.user.domain.vo.Hobby;
import com.prgrms.springboard.user.domain.vo.Name;

import lombok.Getter;

@Getter
public class UserDto {
    private final Long id;
    private final String name;
    private final int age;
    private final String hobby;
    private final List<Post> posts;
    private final String createdBy;
    private final LocalDateTime createdAt;

    public UserDto(Long id, String name, int age, String hobby, List<Post> posts, String createdBy,
        LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.hobby = hobby;
        this.posts = posts;
        this.createdBy = createdBy;
        this.createdAt = createdAt;
    }

    public static UserDto from(User user) {
        return new UserDto(
            user.getId(),
            user.getName().getValue(),
            user.getAge().getValue(),
            user.getHobby().getValue(),
            user.getPosts(),
            user.getCreatedBy(),
            user.getCreatedAt()
        );
    }

    public User toEntity() {
        return new User(createdBy, createdAt, id, new Name(name), new Age(age), new Hobby(hobby));
    }

    public UserResponse toResponse() {
        return new UserResponse(id, name, age, hobby);
    }
}
