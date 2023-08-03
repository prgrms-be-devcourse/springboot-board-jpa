package com.programmers.domain.post.application.converter;

import com.programmers.domain.post.entity.Post;
import com.programmers.domain.post.ui.dto.PostDto;
import com.programmers.domain.post.ui.dto.PostResponseDto;
import com.programmers.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post convertPost(PostDto postDto, User user) {
        return new Post(postDto.title(), postDto.content(), user);
    }

    public PostDto convertPostDto(Post post) {
        return new PostDto(post.getTitle(), post.getContent(), post.getUser().getId());
    }

    public PostResponseDto convertEntityToPostResponseDto(Post post){
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId());
    }
}
