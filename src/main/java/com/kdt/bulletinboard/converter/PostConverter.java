package com.kdt.bulletinboard.converter;

import com.kdt.bulletinboard.dto.PostDto;
import com.kdt.bulletinboard.dto.UserDto;
import com.kdt.bulletinboard.entity.Post;
import com.kdt.bulletinboard.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {
    public Post convertToPost(PostDto postDto) {
        Post post = new Post(postDto.getTitle(), postDto.getContent(), LocalDateTime.now().toString());
        post.setUser(convertToUser(postDto.getUserDto()));
        return post;
    }

    public User convertToUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getHobby(), LocalDateTime.now().toString());
    }

    public PostDto convertToPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(this.convertToUserDto(post.getUser()))
                .build();
    }

    public UserDto convertToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getUserName())
                .hobby(user.getHobby())
                .build();
    }

}
