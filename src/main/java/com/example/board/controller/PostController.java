package com.example.board.controller;

import com.example.board.annotation.AuthUser;
import com.example.board.dto.request.post.CreatePostRequest;
import com.example.board.dto.request.post.PageCondition;
import com.example.board.dto.request.post.PostSearchCondition;
import com.example.board.dto.request.post.UpdatePostRequest;
import com.example.board.dto.response.ApiResponse;
import com.example.board.dto.response.PageResponse;
import com.example.board.dto.response.PostResponse;
import com.example.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createPost(@AuthUser Long userId,
                                                        @RequestBody @Valid CreatePostRequest requestDto,
                                                        UriComponentsBuilder uriComponentsBuilder) {
        Long postId = postService.createPost(userId, requestDto);
        URI location = uriComponentsBuilder.path("/v1/posts/{id}")
                .buildAndExpand(postId)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        ApiResponse<Long> apiResponse = ApiResponse.success(postId);
        return ResponseEntity.created(location).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PostResponse>>> getPosts(@ModelAttribute @Valid PostSearchCondition searchCondition,
                                                                            @ModelAttribute @Valid PageCondition pageCondition) {
        PageResponse<PostResponse> post = postService.getPosts(searchCondition, pageCondition);
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long postId) {
        PostResponse post = postService.getPost(postId);
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> updatePost(@AuthUser Long userId,
                                                        @PathVariable Long postId,
                                                        @RequestBody @Valid UpdatePostRequest requestDto) {
        postService.updatePost(userId, postId, requestDto);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@AuthUser Long userId,
                                                        @PathVariable Long postId) {
        postService.deletePost(userId, postId);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
