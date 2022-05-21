package com.devcourse.springjpaboard.post.controller;

import com.devcourse.springjpaboard.error.NotFoundException;
import com.devcourse.springjpaboard.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.post.controller.dto.UpdatePostRequest;
import com.devcourse.springjpaboard.post.service.dto.PostResponse;
import com.devcourse.springjpaboard.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface PostController {

    ApiResponse<PostResponse> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest updatePostRequest);

    ApiResponse<PostResponse> writePost(@RequestBody CreatePostRequest createPostRequest);

    ApiResponse<PostResponse> getPostById(@PathVariable Long id) throws NotFoundException;

    ApiResponse<Page<PostResponse>> getAllPosts(Pageable pageable);

    ApiResponse<String> internalServerError(Exception e);

    public ApiResponse<String> notFoundHandler(NotFoundException e);
}
