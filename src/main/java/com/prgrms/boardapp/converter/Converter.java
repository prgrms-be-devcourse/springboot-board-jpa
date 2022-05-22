package com.prgrms.boardapp.converter;

import com.prgrms.boardapp.dto.PostDto;
import com.prgrms.boardapp.dto.UserDto;
import com.prgrms.boardapp.model.Post;
import com.prgrms.boardapp.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Converter {

    public Post convertPost(PostDto postDto) {
        Post post = Post.builder()
                .id(postDto.getId())
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .build();
        post.changeUser(this.convertUser(postDto.getUserDto()));
        return post;
    }

    public User convertUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .name(userDto.getName())
                .build();
    }

    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .createdAt(post.getCommonEmbeddable().getCreatedAt())
                .content(post.getContent())
                .title(post.getTitle())
                .userDto(convertUserDto(post.getUser()))
                .build();
    }

    public UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .age(user.getAge())
                .hobby(user.getHobby())
                .name(user.getName())
                .build();
    }

    public List<PostDto> convertPostDtoList(User user) {
        List<PostDto> postDtoList = new ArrayList<>();
        for (int i = 0; i < user.getPosts().size(); i++) {
            postDtoList.add(this.convertPostDto(user.getPosts().get(i)));
        }
        return postDtoList;
    }
}
