package com.devcourse.springjpaboard.application.post.service;

import com.devcourse.springjpaboard.application.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.application.post.controller.dto.UpdatePostRequest;
import com.devcourse.springjpaboard.application.post.service.dto.PostResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostSerivce {

  PostResponse save(CreatePostRequest createPostRequest);

  PostResponse findOne(Long id);

  Page<PostResponse> findAll(Pageable pageable);

  PostResponse update(Long id, UpdatePostRequest updatePostRequest);
}
