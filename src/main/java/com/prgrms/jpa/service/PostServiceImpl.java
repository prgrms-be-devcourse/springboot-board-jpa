package com.prgrms.jpa.service;

import com.prgrms.jpa.controller.dto.CreatePostRequest;
import com.prgrms.jpa.controller.dto.UpdatePostRequest;
import com.prgrms.jpa.domain.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Override
    public long create(CreatePostRequest createPostRequest) {
        return 0;
    }

    @Override
    public List<Post> findAll() {
        return null;
    }

    @Override
    public Post findById(long id) {
        return null;
    }

    @Override
    public void update(long id, UpdatePostRequest updatePostRequest) {

    }
}
