package com.prgrms.jpa.utils;

import com.prgrms.jpa.controller.dto.post.PostResponse;
import com.prgrms.jpa.controller.dto.user.UserResponse;
import com.prgrms.jpa.domain.Post;
import com.prgrms.jpa.domain.User;

public class ToDtoMapper {

    private ToDtoMapper() {
    }

    public static UserResponse toUserDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

    public static PostResponse toPostDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .user(toUserDto(post.getUser()))
                .build();
    }
}
