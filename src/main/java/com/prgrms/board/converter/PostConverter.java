package com.prgrms.board.converter;

import com.prgrms.board.domain.post.Post;
import com.prgrms.board.domain.user.User;
import com.prgrms.board.post.dto.PostDto;
import com.prgrms.board.post.dto.UserDto;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public class PostConverter {
    public Post convertPost(PostDto postDto) {
        System.out.println(postDto.getId());
        System.out.println(postDto.getTitle());
        System.out.println(postDto.getContent());
        return Post.builder()
                .id(postDto.getId())
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(convertUser(postDto.getUserDto()))
                .build();
    }

    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(convertUserDto(post.getUser()))
                .build();
    }

    private User convertUser(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .build();
    }

    private UserDto convertUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }
}
