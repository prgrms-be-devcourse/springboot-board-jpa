package com.prgrms.board.post.controller;

import com.prgrms.board.common.ApiResponse;
import com.prgrms.board.post.dto.PostRequest;
import com.prgrms.board.post.dto.PostResponse;
import com.prgrms.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/{userId}")
    public ApiResponse<PostResponse> save(
            @PathVariable Long userId,
            @RequestBody PostRequest postRequest
    ) {
        PostResponse postResponse = postService.insert(postRequest, userId);
        return ApiResponse.ok(postResponse);
    }

    @GetMapping
    public ApiResponse<Slice<PostResponse>> getAll(
            @RequestParam(required = false) String keyword,
            Pageable pageable
    ) {
        Slice<PostResponse> posts = postService.findAllByKeyword(keyword, pageable);
        return ApiResponse.ok(posts);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getOne(
            @PathVariable Long postId
    ) {
        PostResponse postResponse = postService.findOne(postId);
        return ApiResponse.ok(postResponse);
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<String> deleteOne(
            @PathVariable Long postId
    ) {
        postService.deleteOne(postId);
        return ApiResponse.ok();
    }

}
