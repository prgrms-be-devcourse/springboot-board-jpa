package com.prgrms.springbootboardjpa.dto;

import com.prgrms.springbootboardjpa.entity.Post;
import com.prgrms.springbootboardjpa.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostDtoMapper {
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

    public PostResponse convertPost(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .userId(post.getUser().getId())
                .createdBy(post.getCreatedBy())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }

    public String extractName(Object object) {
        return ((UserResponse) object).getName();
    }
}
