package com.example.springbootboard.converter;

import com.example.springbootboard.domain.Post;
import com.example.springbootboard.domain.User;
import com.example.springbootboard.dto.PostDto;
import com.example.springbootboard.dto.UserDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class PostConverter {
    // dto -> entity
    public Post convertPost(PostDto postDto) {
        return new Post(postDto.getTitle(), postDto.getContent());
    }

    private User convertUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getAge(), userDto.getHobby());
    }

    // entity -> dto
    public PostDto convertPostDto(Post post) {
        return new PostDto(post.getTitle(), post.getContent(), post.getCreatedBy().getId());
    }


    private UserDto convertUserDto(User user) {
        return new UserDto(user.getAge(), user.getHobby(), user.getName());
    }

}