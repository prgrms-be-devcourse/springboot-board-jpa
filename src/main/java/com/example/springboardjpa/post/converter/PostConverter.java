package com.example.springboardjpa.post.converter;

import com.example.springboardjpa.post.domain.Post;
import com.example.springboardjpa.post.dto.PostDto;
import com.example.springboardjpa.user.domain.User;
import com.example.springboardjpa.user.dto.UserDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {

    public Post convertPost(PostDto postDto) {
        Post post = new Post(
                postDto.getUserDto().getName(),
                LocalDateTime.now(),
                postDto.getTitle(),
                postDto.getContent()
        );
        post.setUser(convertUser(postDto.getUserDto()));
        return post;
    }

    private User convertUser(UserDto userDto) {
        User user = new User(
                userDto.getName(),
                LocalDateTime.now(),
                userDto.getName(),
                userDto.getAge(),
                userDto.getHobby()
        );
        return user;
    }

    public PostDto convertPostDto(Post post) {
        PostDto postDto = new PostDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                convertUserDto(post.getUser())
        );
        return postDto;
    }

    private UserDto convertUserDto(User user) {
        UserDto userDto = new UserDto(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getHobby()
        );
        return userDto;
    }
}
