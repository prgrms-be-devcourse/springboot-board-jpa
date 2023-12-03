package com.programmers.springboard.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.programmers.springboard.entity.Post;
import com.programmers.springboard.request.PostSearchRequest;

public interface PostCustomRepository {

	Page<Post> findPostsByCustomCondition(PostSearchRequest request, Pageable pageable);
}
