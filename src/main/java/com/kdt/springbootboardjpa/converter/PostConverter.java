package com.kdt.springbootboardjpa.converter;

import com.kdt.springbootboardjpa.domain.Post;
import com.kdt.springbootboardjpa.domain.dto.PostCreateRequest;
import com.kdt.springbootboardjpa.domain.dto.PostDTO;
import com.kdt.springbootboardjpa.domain.User;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    //DTO to Entity
    public Post convertPost(PostCreateRequest request, User user) {
        return Post.builder()
                .user(user)
                .title(request.getTitle())
                .content(request.getContent())
                .build();
    }

    //entity to DTO
    public PostDTO convertPostDTO(Post post) {
        return new PostDTO(post.getId(), post.getTitle(), post.getContent(), post.getUser().getUsername());
    }
}
