package com.example.springbootboardjpa.converter;

import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.dto.UserDto;
import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.model.User;
import jakarta.validation.Valid;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@Validated
public class PostConverter {
    public Post convertNewPost(PostDTO.Save postDTO) {
        @Valid User user = this.convertUser(postDTO.getUserDto());
        @Valid Post post = new Post(postDTO.getTitle(), postDTO.getContent(), user);
        post.setCreatedBy(postDTO.getUserDto().getName());

        return post;
    }

    private User convertUser(UserDto.Info userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getAge(), userDto.getHobby());
    }

    public PostDTO.Response convertResponseOnlyPostDto(@Valid Post post) {
        PostDTO.Response postDTO = PostDTO.Response.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .build();
        return postDTO;
    }

    private UserDto.Info convertUserDto(@Valid User user) {
        UserDto.Info userDto = UserDto.Info.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
        return userDto;
    }
}
