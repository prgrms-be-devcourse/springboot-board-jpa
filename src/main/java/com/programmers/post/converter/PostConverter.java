package com.programmers.post.converter;

import com.programmers.post.domain.Post;
import com.programmers.post.dto.PostDto;
import com.programmers.user.domain.User;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post convertPost(PostDto postDto, User user) {
        return new Post(postDto.title(), postDto.content(), user);
    }

    public PostDto convertPostDto(Post post) {
        return new PostDto(post.getTitle(), post.getContent(), post.getUser().getId());
    }
}
