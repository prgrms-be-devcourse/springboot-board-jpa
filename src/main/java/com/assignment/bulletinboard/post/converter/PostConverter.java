package com.assignment.bulletinboard.post.converter;

import com.assignment.bulletinboard.post.Post;
import com.assignment.bulletinboard.post.dto.PostDto;
import com.assignment.bulletinboard.user.User;
import com.assignment.bulletinboard.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post convertToPost(PostDto postDto) {
        return Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .build();
    }

    public User convertToUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .age(userDto.getAge())
                .name(userDto.getName())
                .hobby(userDto.getHobby())
                .build();
    }

    public PostDto convertToPostDto (Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .build();
    }

    public UserDto convertToUserDto (User user) {
        return UserDto.builder()
                .id(user.getId())
                .age(user.getAge())
                .name(user.getName())
                .hobby(user.getHobby())
                .build();
    }
}
