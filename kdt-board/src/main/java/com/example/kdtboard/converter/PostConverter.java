package com.example.kdtboard.converter;

import com.example.kdtboard.domain.Post;
import com.example.kdtboard.domain.User;
import com.example.kdtboard.dto.CreatePostRequest;
import com.example.kdtboard.dto.PostDto;
import com.example.kdtboard.dto.UserDto;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.stereotype.Component;

@Component
public class PostConverter{

    public Post convertPostWithUser(CreatePostRequest postRequest, User user){
        return new Post(postRequest.getTitle(), postRequest.getContent(), user);
    }

    public PostDto convertPostDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(convertUserDto(post.getUser()))
                .build();
    }

    public UserDto convertUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
