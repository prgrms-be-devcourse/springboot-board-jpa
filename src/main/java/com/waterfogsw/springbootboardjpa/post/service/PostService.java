package com.waterfogsw.springbootboardjpa.post.service;

import com.waterfogsw.springbootboardjpa.post.entity.Post;

import java.util.List;

public interface PostService {
    void addPost(Post post);

    Post getOne(long id);

    List<Post> getAll();

    void updatePost(long id, Post post);
}
