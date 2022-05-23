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
                postRequestDto.title(),
                postRequestDto.content(),
                this.convertUser(postRequestDto.author()));
    }

    private User convertUser(UserResponseDto userResponseDto) {
        return new User(
                userResponseDto.id(),
                userResponseDto.name(),
                userResponseDto.age(),
                userResponseDto.hobby());
    }

    public PostResponseDto convertPostResponseDto(Post post) {
        return new PostResponseDto(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                this.convertUserResponseDto(post.getAuthor()));
    }

    private UserResponseDto convertUserResponseDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getName(),
                user.getAge(),
                user.getHobby());
    }
}
