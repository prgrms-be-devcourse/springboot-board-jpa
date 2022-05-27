package com.example.demo.converter;

import com.example.demo.domain.Post;
import com.example.demo.domain.User;
import com.example.demo.dto.PostDto;
import com.example.demo.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post convertPost(PostDto postDto) {
        return new Post(postDto.getTitle(), postDto.getContent(), convertUser(postDto.getUserDto()));
    }

    private User convertUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getAge(), userDto.getHobby());
    }

    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(this.convertUserDto(post.getUser()))
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    private UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
