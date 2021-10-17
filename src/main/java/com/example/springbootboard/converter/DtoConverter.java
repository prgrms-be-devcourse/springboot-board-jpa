package com.example.springbootboard.converter;

import com.example.springbootboard.dto.PostRequestDto;
import com.example.springbootboard.dto.PostResponseDto;
import com.example.springbootboard.dto.UserRequestDto;
import com.example.springbootboard.dto.UserResponseDto;
import com.example.springbootboard.entity.Post;
import com.example.springbootboard.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoConverter {

    public User convertUser(UserRequestDto userRequestDto) {
        return User.builder()
                .name(userRequestDto.getName())
                .age(userRequestDto.getAge())
                .hobby(userRequestDto.getHobby())
                .build();
    }

    public User convertUser(UserResponseDto userResponseDto) {
        User user = User.builder()
                .name(userResponseDto.getName())
                .age(userResponseDto.getAge())
                .hobby(userResponseDto.getHobby())
                .build();

        this.convertPosts(userResponseDto).forEach(post -> user.addPost(post));

        return user;
    }

    public UserResponseDto convertUserResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .postDtos(user.getPosts().stream()
                        .map(post -> convertPostResponseDto(post))
                        .collect(Collectors.toList())
                )
                .build();
    }

    public List<Post> convertPosts(UserResponseDto userResponseDto) {
        return userResponseDto.getPostDtos().stream()
                .map(postDto -> Post.builder()
                        .title(postDto.getTitle())
                        .content(postDto.getContent())
                        .build())
                .collect(Collectors.toList());
    }

    public Post convertPost(PostRequestDto postRequestDto) {
        return Post.builder()
                .title(postRequestDto.getTitle())
                .content(postRequestDto.getContent())
                .build();
    }

    public PostResponseDto convertPostResponseDto(Post post) {
        return PostResponseDto.builder()
                .postId(post.getId())
                .userId(post.getUser().getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }
}
