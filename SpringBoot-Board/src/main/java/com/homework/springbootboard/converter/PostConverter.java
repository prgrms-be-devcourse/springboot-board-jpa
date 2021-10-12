package com.homework.springbootboard.converter;

import com.homework.springbootboard.dto.PostDto;
import com.homework.springbootboard.dto.UserDto;
import com.homework.springbootboard.model.Post;
import com.homework.springbootboard.model.User;
import org.springframework.stereotype.Component;


@Component
public class PostConverter {

    public Post convertPost(PostDto postDto) {
        Post post = Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(convertUser(postDto.getUserDto()))
                .build();
        return post;
    }

    private User convertUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .build();

        return user;
    }

    public PostDto convertPostDto(Post post) {
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(this.convertUserDto(post.getUser()))
                .build();

        return postDto;
    }

    private UserDto convertUserDto(User user) {
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();

        return userDto;
    }
}
