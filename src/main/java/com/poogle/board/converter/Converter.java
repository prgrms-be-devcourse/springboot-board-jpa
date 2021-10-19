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
                .userResponse(this.convertUserDto(post.getUser()))
                .build();
    }

    public UserResponse convertUserDto(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
