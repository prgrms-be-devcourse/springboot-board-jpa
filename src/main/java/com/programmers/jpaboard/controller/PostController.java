package com.programmers.jpaboard.controller;

import com.programmers.jpaboard.dto.request.PostCreateRequest;
import com.programmers.jpaboard.dto.response.ApiResponse;
import com.programmers.jpaboard.dto.response.PostCreateResponse;
import com.programmers.jpaboard.dto.response.PostFindResponse;
import com.programmers.jpaboard.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PostCreateResponse> createPost(@RequestBody PostCreateRequest request) {
        PostCreateResponse response = postService.createPost(request);

        return ApiResponse.success(response);
    }

    @GetMapping
    public ApiResponse<List<PostFindResponse>> findAllPosts() {
        List<PostFindResponse> response = postService.findAllPosts();

        return ApiResponse.success(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostFindResponse> findPostById(@PathVariable Long id) {
        PostFindResponse response = postService.findPostById(id);

        return ApiResponse.success(response);
    }
}
