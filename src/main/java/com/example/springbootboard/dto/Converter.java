package com.example.springbootboard.dto;

import com.example.springbootboard.entity.Post;
import com.example.springbootboard.entity.User;

public class Converter {
    public static User dtoToUser(com.example.springbootboard.dto.UserDto dto){
        return User.builder()
                .id(dto.getId())
                .name(dto.getName())
                .age(dto.getAge())
                .hobby(dto.getHobby())
                .build();
    }

    public static com.example.springbootboard.dto.UserDto userToDto(User user){
        return com.example.springbootboard.dto.UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

    public static Post dtoToPost(PostDto dto){
        return Post.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .build();
    }

    public static PostDto postToDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public static User userCreateRequestToUser(UserCreateRequest request){
        return User.builder()
                .name(request.getName())
                .age(request.getAge())
                .hobby(request.getHobby())
                .build();
    }

    public static Post postCreateRequestToPost(PostCreateRequest request){
        return Post.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }
}
