package com.waterfogsw.springbootboardjpa.post.service;

import com.waterfogsw.springbootboardjpa.post.entity.Post;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultPostService implements PostService {
    @Override
    public void addPost(Post post) {

    }

    @Override
    public Post getOne(long id) {
        return null;
    }

    @Override
    public List<Post> getAll() {
        return new ArrayList<>();
    }

    @Override
    public void updatePost(long id, Post post) {

    }
}
