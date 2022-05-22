package com.will.jpapractice.global.converter;

import com.will.jpapractice.domain.post.domain.Post;
import com.will.jpapractice.domain.post.dto.PostRequest;
import com.will.jpapractice.domain.post.dto.PostResponse;
import com.will.jpapractice.domain.user.domain.User;
import com.will.jpapractice.domain.user.dto.UserRequest;
import com.will.jpapractice.domain.user.dto.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .build();
    }

    public User toUser(UserRequest userRequest) {
        return User.builder()
                .name(userRequest.getName())
                .age(userRequest.getAge())
                .build();
    }

    public Post toPost(PostRequest postRequest, User user) {
        return Post.of(postRequest.getTitle(), postRequest.getContent(), user);
    }

    public PostResponse toPostResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
