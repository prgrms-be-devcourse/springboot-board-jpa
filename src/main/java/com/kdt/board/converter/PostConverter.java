package com.kdt.board.converter;

import com.kdt.board.domain.Post;
import com.kdt.board.domain.User;
import com.kdt.board.dto.PostDTO;
import com.kdt.board.dto.UserDTO;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post convertPost(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.saveUser(this.convertUser(postDTO.getUserDTO()));
        return post;
    }

    public User convertUser(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setHobby(userDTO.getHobby());
        user.setId(userDTO.getId());
        return user;
    }

    public PostDTO convertPostDTO(Post post) {
        return PostDTO.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .createdAt(post.getCreatedAt())
            .createdBy(post.getCreatedBy())
            .userDTO(convertUserDTO(post.getUser()))
            .build();
    }

    public UserDTO convertUserDTO(User user) {
        return UserDTO.builder()
            .id(user.getId())
            .name(user.getName())
            .createdAt(user.getCreatedAt())
            .createdBy(user.getCreatedBy())
            .age(user.getAge())
            .hobby(user.getHobby())
            .build();
    }
}
