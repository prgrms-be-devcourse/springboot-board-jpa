package com.toy.board.springbootboard.model.converter;

import com.toy.board.springbootboard.model.domain.Post;
import com.toy.board.springbootboard.model.domain.User;
import com.toy.board.springbootboard.model.dto.PostDto;
import com.toy.board.springbootboard.model.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post convertPost(PostDto postDto){
        Post post = new Post(
                postDto.getTitle(),
                postDto.getContent(),
                this.convertUser(postDto.getUserDto())
        );
        post.setCreatedAt(postDto.getCreatedAt());
        post.setCreatedBy(postDto.getCreatedBy());

        return post;
    }

    public User convertUser(UserDto userDto){
        User user = new User(
                userDto.getName(),
                userDto.getAge(),
                userDto.getHobby()
        );
        user.setCreatedAt(userDto.getCreatedAt());
        user.setCreatedBy(userDto.getCreatedBy());

        return user;
    }

    public PostDto convertPostDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdBy(post.getCreatedBy())
                .createdAt(post.getCreatedAt())
                .build();
    }

    public UserDto converUserDto (User user){
        return UserDto.builder()
                .id(user.getId())
                .age(user.getAge())
                .name(user.getName())
                .hobby(user.getHobby())
                .createdBy(user.getCreatedBy())
                .createdAt(user.getCreatedAt())
                .build();
    }

}
