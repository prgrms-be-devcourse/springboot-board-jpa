package com.prgrms.hyuk.controller;

import com.prgrms.hyuk.dto.ApiResponse;
import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.dto.PostDto;
import com.prgrms.hyuk.dto.PostUpdateRequest;
import com.prgrms.hyuk.exception.InvalidPostIdException;
import com.prgrms.hyuk.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(InvalidPostIdException.class)
    private ApiResponse<String> invalidPostIdHandle(InvalidPostIdException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @PostMapping("/posts")
    public ApiResponse<Long> save(@RequestBody PostCreateRequest postCreateRequest) {
        return ApiResponse.ok(postService.create(postCreateRequest));
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable) {
        return ApiResponse.ok(postService.findPosts(pageable));
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getOne(@PathVariable Long id) {
        return ApiResponse.ok(postService.findPost(id));
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<Long> updatePost(
        @PathVariable Long id,
        @RequestBody PostUpdateRequest postUpdateRequest
    ) {
        return ApiResponse.ok(postService.updatePost(id, postUpdateRequest));
    }
}
