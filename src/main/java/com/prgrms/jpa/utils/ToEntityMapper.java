package com.prgrms.jpa.utils;

import com.prgrms.jpa.controller.dto.post.CreatePostRequest;
import com.prgrms.jpa.controller.dto.user.CreateUserRequest;
import com.prgrms.jpa.domain.Post;
import com.prgrms.jpa.domain.User;

public class ToEntityMapper {

    private ToEntityMapper() {
    }

    public static User toUser(CreateUserRequest createUserRequest) {
        return User.builder()
                .name(createUserRequest.getName())
                .age(createUserRequest.getAge())
                .hobby(createUserRequest.getHobby())
                .build();
    }

    public static Post toPost(CreatePostRequest createPostRequest, User user) {
        return Post.builder()
                .title(createPostRequest.getTitle())
                .content(createPostRequest.getContent())
                .user(user)
                .build();
    }
}
