package com.devcourse.springjpaboard.post.service;

import com.devcourse.springjpaboard.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.post.controller.dto.UpdatePostRequest;
import com.devcourse.springjpaboard.post.service.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {
    PostResponse save(CreatePostRequest dto);

    PostResponse findOne(Long id);

    Page<PostResponse> findAll(Pageable pageable);

    PostResponse update(Long id, UpdatePostRequest postDto);
}
