package com.prgrms.springbootboardjpa.dto;

import com.prgrms.springbootboardjpa.entity.Post;
import com.prgrms.springbootboardjpa.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    public User convertUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());

        return user;
    }

    public UserDto convertUserDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setAge(user.getAge());
        userDto.setHobby(user.getHobby());

        return userDto;
    }

    public Post convertPost(PostDto postDto) {
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setUser(convertUser(postDto.getUserDto()));

        return post;
    }

    public PostDto convertPostDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUserDto(convertUserDto(post.getUser()));

        return postDto;
    }
}
