package com.example.board.controller;

import com.example.board.dto.request.post.*;
import com.example.board.dto.response.ApiResponse;
import com.example.board.dto.response.PageResponse;
import com.example.board.dto.response.PostResponse;
import com.example.board.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<Long>> createPost(@RequestBody @Valid CreatePostRequest requestDto,
                                                        UriComponentsBuilder uriComponentsBuilder) {
        Long id = postService.createPost(requestDto);
        URI location = uriComponentsBuilder.path("/v1/posts/{id}")
                .buildAndExpand(id)
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);

        ApiResponse<Long> apiResponse = ApiResponse.success(id);
        return ResponseEntity.created(location).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<PostResponse>>> getPosts(@ModelAttribute @Valid PostSearchCondition searchCondition,
                                                                            @ModelAttribute @Valid PageCondition pageCondition) {
        pageCondition.updateValidPageCondition(); //? 질문
        Pageable pageable = PageRequest.of(pageCondition.getPage() - 1, pageCondition.getSize());
        PageResponse<PostResponse> post = postService.getPosts(searchCondition, pageable);
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> getPost(@PathVariable Long id) {
        PostResponse post = postService.getPost(id);
        return ResponseEntity.ok(ApiResponse.success(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> updatePost(@PathVariable Long id,
                                                        @RequestBody @Valid UpdatePostRequest requestDto) {
        postService.updatePost(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long id,
                                                        @RequestBody @Valid DeletePostRequest requestDto) {
        postService.deletePost(id, requestDto);
        return ResponseEntity.ok(ApiResponse.success());
    }
}
