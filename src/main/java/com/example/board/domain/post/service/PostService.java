package com.example.board.domain.post.service;

import static com.example.board.domain.post.dto.PostDto.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    SinglePostResponse post(CreatePostRequest createPostRequest);
    Page<SinglePostResponse> pagingPost(Pageable pageable);
    SinglePostResponse getPost(long postId);
    SinglePostResponse updatePost(long postId, UpdatePostRequest updatePostRequest);
}
