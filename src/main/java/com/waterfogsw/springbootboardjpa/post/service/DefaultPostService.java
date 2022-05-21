package com.waterfogsw.springbootboardjpa.post.service;

import com.waterfogsw.springbootboardjpa.post.entity.Post;
import com.waterfogsw.springbootboardjpa.post.repository.PostRepository;
import com.waterfogsw.springbootboardjpa.user.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultPostService implements PostService {

    private final UserService userService;
    private final PostRepository postRepository;

    public DefaultPostService(UserService userService, PostRepository postRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @Override
    public void addPost(long userId, Post post) {

    }

    @Override
    public Post getOne(long id) {
        return null;
    }

    @Override
    public List<Post> getAll(Pageable pageable) {
        return new ArrayList<>();
    }


    @Override
    public void updatePost(long userId, long postId, Post post) {

    }
}
