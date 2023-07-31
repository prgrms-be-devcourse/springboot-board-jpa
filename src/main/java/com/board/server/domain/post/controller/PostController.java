package com.board.server.domain.post.controller;

import com.board.server.domain.post.dto.request.CreatePostRequest;
import com.board.server.domain.post.dto.request.UpdatePostRequest;
import com.board.server.domain.post.dto.response.PostResponse;
import com.board.server.domain.post.dto.response.PostsResponse;
import com.board.server.domain.post.service.PostService;
import com.board.server.global.common.dto.ApiResponse;
import com.board.server.global.exception.Error;
import com.board.server.global.exception.Success;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PostsResponse> getAllPost(
            @RequestParam int page
    ) {
        if (page < 1) {
            return ApiResponse.error(Error.PAGE_REQUEST_VALIDATION_EXCEPTION, Error.PAGE_REQUEST_VALIDATION_EXCEPTION.getMessage());
        }

        return ApiResponse.success(Success.GET_POST_LIST_SUCCESS, postService.getAllPost(page));
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PostResponse> getPost(
            @PathVariable Long postId
    ) {
        return ApiResponse.success(Success.GET_POST_SUCCESS, postService.getPost(postId));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PostResponse> createPost(
            @RequestHeader Long userId,
            @RequestBody CreatePostRequest request
    ) {
        return ApiResponse.success(Success.CREATE_POST_SUCCESS, postService.createPost(request, userId));
    }

    @PatchMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PostResponse> updatePost(
            @PathVariable Long postId,
            @RequestBody UpdatePostRequest request,
            @RequestHeader Long userId
    ) {
        return ApiResponse.success(Success.UPDATE_POST_SUCCESS, postService.updatePost(request, postId, userId));
    }

}
