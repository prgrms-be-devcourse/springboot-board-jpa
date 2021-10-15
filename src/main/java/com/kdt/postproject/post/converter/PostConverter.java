package com.kdt.postproject.post.converter;


import com.kdt.postproject.domain.post.Post;
import com.kdt.postproject.domain.post.User;
import com.kdt.postproject.post.dto.PostDto;
import com.kdt.postproject.post.dto.UserDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class PostConverter {
    // dto -> entity
    public Post convertPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedAt(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        post.setCreatedBy(postDto.getUserDto().getName());

        post.setUser(this.convertUser(postDto.getUserDto()));
        return post;
    }

    private User convertUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());
        return user;
    }

    // entity -> dto
    public PostDto convertPostDto (Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getTitle())
                .userDto(this.convertUserDto(post.getUser()))
                .build();
    }


    private UserDto convertUserDto (User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
