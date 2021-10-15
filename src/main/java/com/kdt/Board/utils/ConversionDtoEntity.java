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

    public PostResponse toPostResponse(Post post) {
        final User user = post.getUser();
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userResponse(UserResponse.builder()
                        .userId(user.getId())
                        .name(user.getName())
                        .hobby(user.getHobby())
                        .age(user.getAge())
                        .createdAt(user.getCreatedAt())
                        .modifiedAt(user.getModifiedAt())
                        .build())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .build();

    }

    public Post toPost(Long userId, PostRequest postRequest) {
        //회원가입 기능이 없으므로, 회원이 존재하지 않으면 임시로 회원 생성
        final User user = userRepository.findById(userId).orElse(User.builder().build());
        return Post.builder()
                .user(user)
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .build();
    }

    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .userId(user.getId())
                .name(user.getName())
                .hobby(user.getHobby())
                .age(user.getAge())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
