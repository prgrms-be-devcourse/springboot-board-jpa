package com.example.board.converter;

import com.example.board.domain.Hobby;
import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.PostDto;
import com.example.board.dto.UserDto;
import com.example.board.repository.PostRepository;
import com.example.board.service.PostService;
import com.example.board.service.UserService;
import javassist.NotFoundException;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    @Autowired
    private UserService userService;

    public Post convertPost(PostDto postDto){
        Post post = new Post();

        post.setTitle(postDto.getTitle());
        try {
            UserDto userDto = postDto.getUserDto();
            userService.find(userDto.getName());
            post.setAuthor(User.getMockUser(userDto.getId()));
        } catch (NotFoundException e){
            post.setAuthor(this.convertUser(postDto.getUserDto()));
        }
        post.setContent(postDto.getContent());
        post.setCreatedAt(postDto.getCreatedAt());
        post.setCreatedBy(postDto.getCreatedBy());

        return post;
    }

    public User convertUser(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setAge(userDto.getAge());
        user.setHobby(Hobby.valueOf(userDto.getHobby()));
        user.setName(userDto.getName());
        user.setCreatedAt(userDto.getCreatedAt());
        user.setCreatedBy(userDto.getCreatedBy());
        return user;
    }

    public PostDto convertPostEntity (Post post){
        return PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .userDto(convertUserEntity(post.getAuthor()))
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .createdBy(post.getCreatedBy())
                .build();
    }

    public UserDto convertUserEntity (User user){
        return UserDto.builder()
                .id(user.getId())
                .age(user.getAge())
                .name(user.getName())
                .hobby(user.getHobby().toString())
                .createdAt(user.getCreatedAt())
                .createdBy(user.getCreatedBy())
                .build();
    }
}
