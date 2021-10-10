package com.programmers.springboard.converter;

import com.programmers.springboard.dto.PostDto;
import com.programmers.springboard.dto.UserDto;
import com.programmers.springboard.model.Post;
import com.programmers.springboard.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {
    public Post convertPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUser(this.convertUser(postDto.getUserDto()));
        post.setCreatedBy(postDto.getUserDto().getName());
        post.setCreatedAt(LocalDateTime.now());
        return post;
    }

    public User convertUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());
        user.setCreatedBy(userDto.getName());
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }

    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(this.convertUserDto(post.getUser()))
                .createdBy(post.getCreatedBy())
                .build();
    }

    public UserDto convertUserDto(User user) {
        return UserDto.builder()
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
