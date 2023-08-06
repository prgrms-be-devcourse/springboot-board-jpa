package com.juwoong.springbootboardjpa.user.application.model;

import java.time.LocalDateTime;
import java.util.List;
import com.juwoong.springbootboardjpa.post.domain.Post;
import com.juwoong.springbootboardjpa.user.domain.User;
import com.juwoong.springbootboardjpa.user.domain.constant.Hobby;

public record UserDto(Long id, String name, int age, Hobby hobby, List<Post> posts, LocalDateTime createdAt,
                      LocalDateTime updatedAt) {
    public UserDto(User user){
        this(user.getId(), user.getName(), user.getAge(), user.getHobby(), user.getPosts(), user.getCreatedAt(), user.getUpdatedAt());
    }
}
