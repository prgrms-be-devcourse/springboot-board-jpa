package com.prgrms.boardapp.service;

import com.prgrms.boardapp.dto.PostRequest;
import com.prgrms.boardapp.dto.PostResponse;
import com.prgrms.boardapp.dto.UserResponse;
import com.prgrms.boardapp.model.Post;
import com.prgrms.boardapp.model.User;
import org.springframework.stereotype.Component;

@Component
class Converter {
    Post convertPost(PostRequest postDto) {
        return Post.builder()
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .build();
    }

    PostResponse convertPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .title(post.getTitle())
                .user(convertUserResponse(post.getUser()))
                .build();
    }

    private UserResponse convertUserResponse(User user) {
        return UserResponse.builder()
                .age(user.getAge())
                .hobby(user.getHobby())
                .name(user.getName())
                .id(user.getId())
                .build();
    }
}
