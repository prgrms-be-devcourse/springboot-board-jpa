package com.kdt.programmers.forum.utils;

import com.kdt.programmers.forum.transfer.PostDto;
import com.kdt.programmers.forum.domain.Post;
import com.kdt.programmers.forum.transfer.request.PostRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {
    public Post toPost(PostRequest request) {
        Post post = new Post();
        post.update(request.getTitle(), request.getContent());
        post.setCreatedAt(LocalDateTime.now());

        return post;
    }

    public PostDto toPostDto(Post post) {
        return new PostDto(post);
    }
}
