package com.assignment.board.dto;

import com.assignment.board.domain.Post;
import com.assignment.board.domain.User;
import org.springframework.stereotype.Component;

@Component
public class DtoConverter {
    //dto -> entity
    public Post convertPost(PostDto postDto) {
       return   Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(this.convertUser(postDto.getUserDto()))
                 .build();
    }

    public static User convertUser(UserDto userDto) {
        return User.builder()
                .age(userDto.getAge())
                .name(userDto.getName())
                .hobby((userDto.getHobby()))
                .build();
    }


    //entity -> dto
    public PostDto convertPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userDto(this.convertUserDto(post.getUser()))
                .build();
    }

    public UserDto convertUserDto(User user){

        return UserDto.builder()
                .id(user.getId())
                .age(user.getAge())
                .name(user.getName())
                .hobby(user.getHobby())
                .build();
    }
}
