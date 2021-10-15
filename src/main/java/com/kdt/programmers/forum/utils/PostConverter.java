package com.kdt.programmers.forum.utils;

import com.kdt.programmers.forum.transfer.PostDto;
import com.kdt.programmers.forum.domain.Post;
import com.kdt.programmers.forum.transfer.request.PostRequest;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PostConverter {
    public Post convertToPost(PostRequest postRequest) {
        Post post = new Post();
        post.update(postRequest.getTitle(), postRequest.getContent());
        post.setCreatedAt(LocalDateTime.now());
        return post;
    }

    public PostDto convertToPostDto(Post post) {
        return new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt());
    }
}
