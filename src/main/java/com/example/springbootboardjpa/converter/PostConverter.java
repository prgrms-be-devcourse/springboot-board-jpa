package com.example.springbootboardjpa.converter;

import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.dto.UserDto;
import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.model.User;

public class PostConverter {
    public Post convertPost(PostDTO postDTO){
        User user = this.convertUser(postDTO.getUserDto());
        Post post = new Post(postDTO.getTitle(),postDTO.getContent(),user);
        post.setCreatedBy(postDTO.getUserDto().getName());

        return post;
    }

    private User convertUser(UserDto userDto){
        User user = new User(userDto.getName(),userDto.getAge(),userDto.getHobby());
        return user;
    }

    public PostDTO convertPostDto(Post post){
        PostDTO postDTO = PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(this.convertUserDto(post.getUser()))
                .build();
        return postDTO;
    }

    private UserDto convertUserDto(User user){
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
        return userDto;
    }
}
