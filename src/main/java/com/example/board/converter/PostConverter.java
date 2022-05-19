package com.example.board.converter;

import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.PostDto;
import com.example.board.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post convertPost(PostDto postDto) {
        return new Post(
                postDto.getTitle(),
                postDto.getContent(),
                this.convertUser(postDto.getAuthor()));
    }

    private User convertUser(UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getName(),
                userDto.getAge(),
                userDto.getHobby());
    }

    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(this.convertUserDto(post.getAuthor()))
                .build();
    }

    private UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
