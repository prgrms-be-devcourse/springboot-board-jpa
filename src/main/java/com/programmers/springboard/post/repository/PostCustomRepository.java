package com.programmers.springboard.post.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.programmers.springboard.post.domain.Post;
import com.programmers.springboard.post.dto.PostSearchRequest;

public interface PostCustomRepository {

	Page<Post> findPostsByCustomCondition(PostSearchRequest request, Pageable pageable);
}
