package com.programmers.jpa_board.global.converter;

import com.programmers.jpa_board.post.domain.Post;
import com.programmers.jpa_board.post.domain.dto.request.CreatePostRequest;
import com.programmers.jpa_board.post.domain.dto.response.PostResponse;
import com.programmers.jpa_board.user.domain.User;
import com.programmers.jpa_board.user.domain.dto.request.CreateUserRequest;
import com.programmers.jpa_board.user.domain.dto.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class BoardConverter {
    public User createUsertoUser(CreateUserRequest dto) {
        return User.builder()
                .name(dto.getName())
                .age(dto.getAge())
                .hobby(dto.getHobby())
                .build();
    }

    public UserResponse userToDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .posts(user.getPosts())
                .createAt(user.getCreatedAt())
                .build();
    }

    public Post createPostToPost(CreatePostRequest dto) {
        return Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    public PostResponse postToDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .createdBy(post.getUser().getName())
                .createAt(post.getCreatedAt())
                .build();
    }
}
