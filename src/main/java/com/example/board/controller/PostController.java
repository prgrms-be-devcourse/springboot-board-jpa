package com.example.board.controller;

import com.example.board.dto.request.CreatePostRequest;
import com.example.board.dto.request.UpdatePostRequest;
import com.example.board.dto.response.ApiResponse;
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

        ApiResponse<Long> apiResponse = ApiResponse.success(HttpStatus.CREATED, id);
        return new ResponseEntity<>(apiResponse, headers, apiResponse.getStatusCode());
    }

    //TODO: 전체 조회

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> getPost(@PathVariable Long id) {
        PostResponse post = postService.getPost(id);
        return ApiResponse.success(HttpStatus.OK, post);
    }

    @PutMapping("/{id}")
    public ApiResponse<Void> updatePost(@PathVariable Long id,
                                        @RequestBody @Valid UpdatePostRequest requestDto) {
        postService.updatePost(id, requestDto);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ApiResponse.success(HttpStatus.NO_CONTENT);
    }
}
