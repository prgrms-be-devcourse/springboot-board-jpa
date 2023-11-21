package com.prgrms.dev.springbootboardjpa.converter;

import com.prgrms.dev.springbootboardjpa.domain.post.Post;
import com.prgrms.dev.springbootboardjpa.domain.user.User;
import com.prgrms.dev.springbootboardjpa.dto.PostDto;
import com.prgrms.dev.springbootboardjpa.dto.PostRequestDto;
import com.prgrms.dev.springbootboardjpa.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    // entity -> dto
    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(this.convertUserDto(post.getUser()))
                .build();
    }

    // dto -> entity
    public Post convertPost(PostRequestDto postDto) {
        return Post.builder()
                .title(postDto.title())
                .content(postDto.content())
                .build();
    }

    // entity -> dto
    public UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .hobby(user.getHobby())
                .age(user.getAge())
                .build();
    }
}
