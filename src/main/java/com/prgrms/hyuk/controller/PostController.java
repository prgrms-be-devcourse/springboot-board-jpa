package com.prgrms.hyuk.controller;

import com.prgrms.hyuk.dto.ApiResponse;
import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.service.PostService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ApiResponse<Long> save(@RequestBody PostCreateRequest postCreateRequest) {
        return ApiResponse.ok(postService.create(postCreateRequest));
    }
}
