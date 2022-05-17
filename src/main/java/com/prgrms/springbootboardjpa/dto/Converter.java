package com.prgrms.springbootboardjpa.dto;

import com.prgrms.springbootboardjpa.entity.Post;
import com.prgrms.springbootboardjpa.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public Post convertToPostEntity(PostDto postDto) {
        Post post = new Post(
            postDto.getId(),
            postDto.getTitle(),
            postDto.getContent(),
            this.convertToUserEntity(postDto.getAuthor()
            ));
        post.setCreatedAt(LocalDateTime.now());
        post.setCreatedBy(postDto.getAuthor().getName());

        return post;
    }

    public User convertToUserEntity(UserDto userDto) {
        List<Post> posts = userDto.getPosts()
            .stream()
            .map(this::convertToPostEntity)
            .collect(Collectors.toList());

        return new User(userDto.getId(), userDto.getName(), userDto.getAge(), userDto.getHobby(),
            posts);
    }

    public PostDto convertToPostDto(Post post) {

        return new PostDto(post.getId(), post.getTitle(), post.getContent(),
            this.convertToUserDto(post.getAuthor()));
    }

    public UserDto convertToUserDto(User user) {
        List<PostDto> posts = user.getPosts()
            .stream()
            .map(this::convertToPostDto)
            .collect(Collectors.toList());

        return new UserDto(user.getId(), user.getName(), user.getAge(), user.getHobby(), posts);
    }


}
