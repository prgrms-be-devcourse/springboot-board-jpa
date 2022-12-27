package com.example.springbootboardjpa.converter;

import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.dto.UserDto;
import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.model.User;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post convertNewPost(PostDTO.Save postDTO) {
        User user = this.convertUser(postDTO.getUserDto());
        Post post = new Post(postDTO.getTitle(), postDTO.getContent(), user);
        post.setCreatedBy(postDTO.getUserDto().getName());

        return post;
    }

    private User convertUser(UserDto.Info userDto) {
        return new User(userDto.getId(), userDto.getName(), userDto.getAge());
    }

    public PostDTO.Response convertResponseOnlyPostDto(Post post) {
        PostDTO.Response postDTO = PostDTO.Response.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .build();
        return postDTO;
    }

    private UserDto.Info convertUserDto(User user) {
        UserDto.Info userDto = UserDto.Info.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .build();
        return userDto;
    }
}
