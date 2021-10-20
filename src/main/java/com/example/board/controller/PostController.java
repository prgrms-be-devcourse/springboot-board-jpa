package com.example.board.controller;

import com.example.board.ApiResponse;
import com.example.board.dto.PostRequest;
import com.example.board.dto.PostResponse;
import com.example.board.exception.PostNotFoundException;
import com.example.board.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ApiResponse<String> postNotFoundHandler(PostNotFoundException e) {
        return ApiResponse.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e) {
        return ApiResponse.fail(e.getMessage());
    }

    @GetMapping
    public ApiResponse<Page<PostResponse>> requestPageOfPosts(Pageable pageable) {
        Page<PostResponse> posts = postService.findAll(pageable);
        return ApiResponse.ok(posts);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> requestOnePost(@PathVariable Long id) {
        PostResponse post = postService.findById(id);
        return ApiResponse.ok(post);
    }

    @PostMapping
    public ApiResponse<Long> createPost(@RequestBody PostRequest request) {
        Long savedPostId = postService.save(request);
        return ApiResponse.ok(savedPostId);
    }

    @PostMapping("/{id}")
    public ApiResponse<Long> editPost(@PathVariable Long id, @RequestBody PostRequest request) {
        Long postId = postService.editPost(id, request);
        return ApiResponse.ok(postId);
    }

}
