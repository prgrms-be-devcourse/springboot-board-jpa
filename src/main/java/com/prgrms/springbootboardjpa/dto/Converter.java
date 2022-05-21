package com.prgrms.springbootboardjpa.dto;

import com.prgrms.springbootboardjpa.entity.Post;
import com.prgrms.springbootboardjpa.entity.User;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Converter {

    public Post convertToPostEntity(PostDto postDto) {
        Post post = new Post(
            postDto.getTitle(),
            postDto.getContent()
        );

        post.setAuthor(this.convertToUserEntity(postDto.getUserDto()));
        post.setCreatedAt(LocalDateTime.now());
        post.setCreatedBy(postDto.getUserDto().getName());

        return post;
    }

    public User convertToUserEntity(UserDto userDto) {
        return new User(userDto.getName(), userDto.getAge(), userDto.getHobby());
    }

    public PostDto convertToPostDto(Post post) {

        return new PostDto(post.getTitle(), post.getContent(),
            this.convertToUserDto(post.getAuthor()));
    }

    public UserDto convertToUserDto(User user) {
        return new UserDto(user.getName(), user.getAge(), user.getHobby());
    }


}
