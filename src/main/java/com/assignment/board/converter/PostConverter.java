package com.assignment.board.converter;

import com.assignment.board.dto.post.PostRequestDto;
import com.assignment.board.dto.post.PostResponseDto;
import com.assignment.board.dto.user.UserResponseDto;
import com.assignment.board.entity.Post;
import com.assignment.board.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {

    public Post convertPost(PostRequestDto postRequestDto) {
        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setCreatedAt(LocalDateTime.now());

        return post;
    }

    public UserResponseDto convertUserDto(User user) {

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setName(user.getName());
        userResponseDto.setAge(user.getAge());
        userResponseDto.setHobby(user.getHobby());

        return userResponseDto;
    }

    public PostResponseDto convertPostDto(Post post) {

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(post.getId());
        postResponseDto.setTitle(post.getTitle());
        postResponseDto.setContent(post.getContent());
        postResponseDto.setUserDto(this.convertUserDto(post.getUser()));
        postResponseDto.setCreatedAt(post.getCreatedAt());
        postResponseDto.setCreatedBy(post.getCreatedBy());

        return postResponseDto;
    }

}
