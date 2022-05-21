package com.kdt.prgrms.board.service;

import com.kdt.prgrms.board.dto.PostAddRequest;
import com.kdt.prgrms.board.dto.PostResponse;
import com.kdt.prgrms.board.dto.PostUpdateRequest;
import com.kdt.prgrms.board.entity.post.Post;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    public void addPost(PostAddRequest postAddRequest) {

    }

    public List<PostResponse> getPosts() {

        return null;
    }

    public PostResponse getPostById(long id) {

        return null;
    }

    public void updatePostById(long id, PostUpdateRequest postUpdateRequest) {

    }
}
