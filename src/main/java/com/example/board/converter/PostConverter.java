package com.example.board.converter;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.PostRequestDto;
import com.example.board.dto.PostResponseDto;
import com.example.board.dto.UserResponseDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post convertPost(PostRequestDto postRequestDto) {
        return new Post(
                postRequestDto.getTitle(),
                postRequestDto.getContent(),
                this.convertUser(postRequestDto.getAuthor()));
    }

    private User convertUser(UserResponseDto userResponseDto) {
        return new User(
                userResponseDto.getId(),
                userResponseDto.getName(),
                userResponseDto.getAge(),
                userResponseDto.getHobby());
    }

    public PostResponseDto convertPostResponseDto(Post post) {
        return PostResponseDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(this.convertUserResponseDto(post.getAuthor()))
                .build();
    }

    private UserResponseDto convertUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
