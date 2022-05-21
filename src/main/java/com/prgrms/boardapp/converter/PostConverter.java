package com.prgrms.boardapp.converter;

import com.prgrms.boardapp.dto.PostDto;
import com.prgrms.boardapp.dto.UserDto;
import com.prgrms.boardapp.model.Post;
import com.prgrms.boardapp.model.User;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post convertPost(PostDto postDto) {
        return Post.builder()
                .id(postDto.getId())
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .user(this.convertUser(postDto.getUserDto()))
                .build();

    }

    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .createdAt(post.getCommonEmbeddable().getCreatedAt())
                .content(post.getContent())
                .title(post.getTitle())
                .userDto(convertUserDto(post.getUser()))
                .build();
    }

    private UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .age(user.getAge())
                .hobby(user.getHobby())
                .name(user.getName())
                .build();
    }

    private User convertUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .name(userDto.getName())
                .build();
    }
}
