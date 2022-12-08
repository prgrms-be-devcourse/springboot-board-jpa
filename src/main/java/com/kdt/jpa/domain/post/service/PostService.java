package com.kdt.jpa.domain.post.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kdt.jpa.domain.post.dto.PostRequest;
import com.kdt.jpa.domain.post.dto.PostResponse;

public interface PostService {
	PostResponse.WritePostResponse write(PostRequest.WritePostRequest request);

	PostResponse findById(Long id);

	PostResponse.UpdatePostResponse update(Long postId, PostRequest.UpdatePostRequest request);

	Page<PostResponse> findAll(Pageable pageable);
}
