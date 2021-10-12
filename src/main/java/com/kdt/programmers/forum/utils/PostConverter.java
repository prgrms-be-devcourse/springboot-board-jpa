package com.kdt.programmers.forum.utils;

import com.kdt.programmers.forum.transfer.PostDto;
import com.kdt.programmers.forum.domain.Post;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    public Post convertToPost(PostDto dto) {
        return new Post(dto.getId(), dto.getTitle(), dto.getContent());
    }

    public PostDto convertToPostDto(Post post) {
        return new PostDto(post.getId(), post.getTitle(), post.getContent());
    }
}
