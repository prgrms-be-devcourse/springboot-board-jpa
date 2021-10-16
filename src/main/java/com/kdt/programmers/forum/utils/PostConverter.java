package com.kdt.programmers.forum.utils;

import com.kdt.programmers.forum.transfer.PostWrapper;
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

    public PostWrapper convertToPostDto(Post post) {
        return new PostWrapper(post.getId(), post.getTitle(), post.getContent(), post.getCreatedAt());
    }
}
