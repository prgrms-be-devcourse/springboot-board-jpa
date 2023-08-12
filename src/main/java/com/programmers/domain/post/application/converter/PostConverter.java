package com.programmers.domain.post.application.converter;

import com.programmers.domain.post.entity.Post;
import com.programmers.domain.post.ui.dto.PostCreateDto;
import com.programmers.domain.post.ui.dto.PostResponseDto;
import com.programmers.domain.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post convertPost(PostCreateDto postCreateDto, User user) {
        return new Post(postCreateDto.title(), postCreateDto.content(), user);
    }

    public PostCreateDto convertPostDto(Post post) {
        return new PostCreateDto(post.getTitle(), post.getContent(), post.getUser().getId());
    }

    public PostResponseDto convertEntityToPostResponseDto(Post post){
        return new PostResponseDto(post.getId(), post.getTitle(), post.getContent(), post.getUser().getId());
    }
}
