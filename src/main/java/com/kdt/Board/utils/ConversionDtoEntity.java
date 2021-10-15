package com.kdt.Board.utils;

import com.kdt.Board.dto.PostRequest;
import com.kdt.Board.dto.PostResponse;
import com.kdt.Board.dto.UserResponse;
import com.kdt.Board.entity.Post;
import com.kdt.Board.entity.User;
import com.kdt.Board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ConversionDtoEntity {

    private final UserRepository userRepository;

    public PostResponse toPostDto(Post post) {
        final User user = post.getUser();
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userResponse(UserResponse.builder()
                        .name(user.getName())
                        .hobby(user.getHobby())
                        .createdAt(user.getCreatedAt())
                        .modifiedAt(user.getModifiedAt())
                        .build())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();

    }

    public Post toPost(Long userId, PostRequest postRequest) {
        final User user = userRepository.findById(userId).get();
        return Post.builder()
                .user(user)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();
    }

}
