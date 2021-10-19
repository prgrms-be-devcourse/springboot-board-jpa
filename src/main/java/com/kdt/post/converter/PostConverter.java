package com.kdt.post.converter;

import com.kdt.post.dto.PostDto;
import com.kdt.post.dto.PostControlRequestDto;
import com.kdt.post.model.Post;
import com.kdt.user.dto.UserDto;
import com.kdt.user.model.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {
    public Post convertPost(Long userId, PostDto postDto){
        LocalDateTime now = LocalDateTime.now();

        Post post = Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .createdAt(now)
                .createdBy(userId.toString())
                .lastUpdatedAt(now)
                .build();

        return post;
    }

    public Post convertPost(PostControlRequestDto postControlRequestDto){
        LocalDateTime now = LocalDateTime.now();

        Post post = Post.builder()
                .id(postControlRequestDto.getPostId())
                .title(postControlRequestDto.getTitle())
                .content(postControlRequestDto.getContent())
                .createdAt(now)
                .createdBy(postControlRequestDto.getUserId().toString())
                .lastUpdatedAt(now)
                .build();

        return post;
    }

    public PostDto convertPostDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .lastUpdatedAt(post.getLastUpdatedAt())
                .userDto(this.convertUserDto(post.getUser()))
                .build();
    }

    private UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .age(user.getAge())
                .name(user.getName())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .lastUpdatedAt(user.getLastUpdatedAt())
                .build();
    }
}
