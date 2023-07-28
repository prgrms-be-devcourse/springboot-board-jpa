package com.jpaboard.domain.post.application;

import com.jpaboard.domain.post.dto.PostCreateRequest;
import com.jpaboard.domain.post.dto.PostResponse;
import com.jpaboard.domain.post.dto.PostUpdateRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

    Long createPost(PostCreateRequest request);

    PostResponse findPostById(Long id);

    Page<PostResponse> findPosts(Pageable pageable);

    Page<PostResponse> findPostByTitle(String title, Pageable pageable);

    Page<PostResponse> findPostByContent(String content, Pageable pageable);

    Page<PostResponse> findByKeyword(String keyword, Pageable pageable);

    void updatePost(Long id, PostUpdateRequest request);

    void deletePostById(Long id);
}
