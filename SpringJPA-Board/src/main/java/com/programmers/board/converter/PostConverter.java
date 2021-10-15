package com.programmers.board.converter;

import com.programmers.board.domain.Post;
import com.programmers.board.domain.User;
import com.programmers.board.dto.PostDto;
import com.programmers.board.dto.UserDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {

    public Post convertPost(PostDto postDto){ // dto -> entity
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setCreatedBy(postDto.getUserDto().getName());
        post.setCreatedAt(LocalDateTime.now());

        post.setUser(this.convertUser(postDto.getUserDto()));
        return post;
    }

    public User convertUser(UserDto userDto){ // dto -> entity
        User user = new User();
        user.setId(userDto.getId());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());
        return user;
    }
}
