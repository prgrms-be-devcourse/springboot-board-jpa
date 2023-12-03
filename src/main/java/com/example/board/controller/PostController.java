package com.example.board.controller;

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
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<ApiResponse<Long>> createPost(@RequestBody @Valid CreatePostRequest requestDto) {
        Long id = postService.createPost(requestDto);
        URI location = UriComponentsBuilder
                .fromPath("/v1/posts/{id}")
                .buildAndExpand(id)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        return new ResponseEntity<>(ApiResponse.success(id), headers, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PostResponse>>> getPosts(@ModelAttribute @Valid PostSearchCondition searchCondition,
                                                                            @ModelAttribute @Valid PageCondition pageCondition) {
        PageResponse<PostResponse> post = postService.getPosts(searchCondition, pageCondition);
        return new ResponseEntity<>(ApiResponse.success(post), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long id) {
        PostResponse post = postService.getPost(id);
        return new ResponseEntity<>(ApiResponse.success(post), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePost(@PathVariable Long id,
                                                        @RequestBody @Valid UpdatePostRequest requestDto) {
        postService.updatePost(id, requestDto);
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return new ResponseEntity<>(ApiResponse.success(), HttpStatus.NO_CONTENT);
    }
}
