package com.pppp0722.boardjpa.post.converter;

import com.pppp0722.boardjpa.domain.post.Post;
import com.pppp0722.boardjpa.domain.post.User;
import com.pppp0722.boardjpa.post.dto.PostDto;
import com.pppp0722.boardjpa.post.dto.UserDto;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post convertPost(PostDto postDto) {
        Post post = new Post();
        post.setCreatedAt(LocalDateTime.now());
        post.setCreatedBy(postDto.getUserDto().getName());
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUser(convertUser(postDto.getUserDto()));

        return post;
    }

    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
            .createdAt(post.getCreatedAt())
            .createdBy(post.getCreatedBy())
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .userDto(convertUserDto(post.getUser()))
            .build();
    }

    public User convertUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());

        return user;
    }

    public UserDto convertUserDto(User user) {
        return UserDto.builder()
            .createdAt(user.getCreatedAt())
            .createdBy(user.getCreatedBy())
            .id(user.getId())
            .name(user.getName())
            .age(user.getAge())
            .hobby(user.getHobby())
            .build();
    }
}
