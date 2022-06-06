package com.devcourse.springjpaboard.post.controller;

import com.devcourse.springjpaboard.exception.NotFoundException;
import com.devcourse.springjpaboard.post.controller.dto.CreatePostRequest;
import com.devcourse.springjpaboard.post.controller.dto.UpdatePostRequest;
import com.devcourse.springjpaboard.post.service.PostService;
import com.devcourse.springjpaboard.post.service.dto.PostResponse;
import com.devcourse.springjpaboard.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ApiResponse<String> internalServerError(Exception e) {
        return ApiResponse.fail(INTERNAL_SERVER_ERROR, e.getMessage());
    }

    @GetMapping("")
    public ApiResponse<Page<PostResponse>> getAllPosts(Pageable pageable) {
        Page<PostResponse> posts = postService.findAll(pageable);
        return ApiResponse.ok(OK, posts);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPostById(@PathVariable Long id) throws NotFoundException {
        PostResponse post = postService.findOne(id);
        return ApiResponse.ok(OK, post);
    }

    @PostMapping("")
    public ApiResponse<PostResponse> writePost(@RequestBody CreatePostRequest createPostRequest) {
        PostResponse post = postService.save(createPostRequest);
        return ApiResponse.ok(OK, post);
    }

    @PutMapping("/{id}")
    public ApiResponse<PostResponse> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequest updatePostRequest)
            throws NotFoundException {
        PostResponse updatePost = postService.update(id, updatePostRequest);
        return ApiResponse.ok(OK, updatePost);
    }
}
