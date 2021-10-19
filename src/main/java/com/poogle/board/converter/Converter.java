package com.poogle.board.converter;

import com.poogle.board.controller.post.PostResponse;
import com.poogle.board.controller.user.UserResponse;
import com.poogle.board.model.post.Post;
import com.poogle.board.model.user.User;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public PostResponse convertPostDto(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .modifiedAt(post.getModifiedAt())
                .createdBy(post.getCreatedBy())
                .modifiedBy(post.getModifiedBy())
//                .userResponse(this.convertUserDto(post.getUser())) //TODO: User 로그인 기능 구현 후 반영
                .build();
    }

    public UserResponse convertUserDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
