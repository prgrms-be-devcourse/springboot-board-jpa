package com.waterfogsw.springbootboardjpa.post.service;

import com.waterfogsw.springbootboardjpa.post.entity.Post;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    void addPost(long userId, Post post);

    Post getOne(long postId);

    List<Post> getAll(Pageable pageable);

    void updatePost(long userId, long postId, Post post);
}
