package com.example.boardbackend.dto.converter;

import com.example.boardbackend.domain.Post;
import com.example.boardbackend.domain.User;
import com.example.boardbackend.domain.embeded.Email;
import com.example.boardbackend.dto.PostDto;
import com.example.boardbackend.dto.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DtoConverter {

    // Entity -> DTO

    public UserDto convertToUserDto(User user){
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail().getAddress())
                .password(user.getPassword())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .createdAt(user.getCreatedAt())
                .build();
    }

    public PostDto convertToPostDto(Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .view(post.getView())
                .userDto(this.convertToUserDto(post.getUser()))
                .createAt(post.getCreatedAt())
                .build();
    }

    // -----------------------------------------------------------------------------------------------------

    // DTO -> Entity

    public User convertToUserEntity(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setEmail(new Email(userDto.getEmail()));
        user.setPassword(userDto.getPassword());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setHobby(userDto.getHobby());
        user.setCreatedAt(userDto.getCreatedAt());
        return user;
    }

    public Post convetToPostEntity(PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setView(postDto.getView());
        post.setUser(this.convertToUserEntity(postDto.getUserDto()));
        post.setCreatedAt(postDto.getCreateAt());
        return post;
    }

}
