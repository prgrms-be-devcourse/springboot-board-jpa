package com.prgrms.springbootboardjpa.dto;

import com.prgrms.springbootboardjpa.entity.Post;
import com.prgrms.springbootboardjpa.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DtoMapper {
    public User convertUser(UserRequest userRequest) {
        User user = new User();
        user.setName(userRequest.getName());
        user.setAge(userRequest.getAge());
        user.setHobby(userRequest.getHobby());

        return user;
    }

    public Post convertPost(PostRequest postRequest) {
        UserResponse userResponse = (UserResponse) postRequest.getUser();
        User user = new User();
        user.setId(userResponse.getId());
        user.setName(userResponse.getName());
        user.setAge(userResponse.getAge());
        user.setHobby(userResponse.getHobby());

        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setContent(postRequest.getContent());
        post.setUser(user);

        return post;
    }

    public UserResponse convertUser(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .age(user.getAge())
                .hobby(user.getHobby())
                .build();
    }

    public PostResponse convertPost(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .build();
    }
}
