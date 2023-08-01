package com.programmers.jpaboard.controller;

import com.programmers.jpaboard.dto.request.PostCreateRequest;
import com.programmers.jpaboard.dto.request.PostUpdateRequest;
import com.programmers.jpaboard.dto.response.ApiResponse;
import com.programmers.jpaboard.dto.response.PostDetailResponse;
import com.programmers.jpaboard.dto.response.PostIdResponse;
import com.programmers.jpaboard.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
    public ApiResponse<PostIdResponse> createPost(@RequestBody PostCreateRequest request) {
        PostIdResponse response = postService.createPost(request);

        return ApiResponse.success(response);
    }

    @GetMapping
    public ApiResponse<List<PostDetailResponse>> findAllPosts() {
        List<PostDetailResponse> response = postService.findAllPosts();

        return ApiResponse.success(response);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDetailResponse> findPostById(@PathVariable Long id) {
        PostDetailResponse response = postService.findPostById(id);

        return ApiResponse.success(response);
    }

    @PatchMapping("/{id}")
    public ApiResponse<PostIdResponse> updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest request) {
        PostIdResponse response = postService.updatePost(id, request);

        return ApiResponse.success(response);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> updatePost(@PathVariable Long id) {
        postService.deletePostById(id);

        return ApiResponse.success();
    }
}
