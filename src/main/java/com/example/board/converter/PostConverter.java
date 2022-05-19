package com.example.board.converter;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.PostDto;
import com.example.board.dto.UserDto;

import java.util.stream.Collectors;

public class PostConverter {
    public Post convertPost(PostDto postDto) {
        return Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .author(this.convertUser(postDto.getAuthor()))
                .build();
    }

    private User convertUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .posts(userDto.getPostDtos().stream()
                        .map(this::convertPost)
                        .collect(Collectors.toList()))
                .build();
    }

    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(this.convertUserDto(post.getAuthor()))
                .build();
    }

    private UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .postDtos(user.getPosts().stream()
                        .map(this::convertPostDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
