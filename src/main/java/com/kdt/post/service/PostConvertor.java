package com.kdt.post.service;

import com.kdt.domain.post.Post;
import com.kdt.domain.user.User;
import com.kdt.post.dto.PostDto;
import com.kdt.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class PostConvertor {

    public Post convertPostDtoToPost(PostDto postDto) {
        return Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(convertUserDtoToUser(postDto.getUserDto()))
                .build();
    }

    private User convertUserDtoToUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .age(userDto.getAge())
                .build();
        return user;
    }

}
