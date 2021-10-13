package com.programmers.springbootboard.controller;

import com.programmers.springbootboard.dto.ApiResponse;
import com.programmers.springbootboard.dto.PostRequestDto;
import com.programmers.springbootboard.dto.PostResponseDto;
import com.programmers.springbootboard.entity.Post;
import com.programmers.springbootboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/api/v1/posts")
    public ApiResponse<Page<PostResponseDto>> getPosts(Pageable pageable) {
        return ApiResponse.ok(HttpMethod.GET, postService.readPostPage(pageable));
    }

    @GetMapping("/api/v1/posts/{id}")
    public ApiResponse<PostResponseDto> getPost(@PathVariable Long id) {
        return ApiResponse.ok(HttpMethod.GET, postService.readPost(id));
    }

    @PostMapping("/api/v1/posts")
    public ApiResponse<PostResponseDto> createPost(@RequestBody PostRequestDto dto) {
        return ApiResponse.ok(HttpMethod.POST, postService.createPost(dto));
    }

    @PutMapping("api/v1/posts/{id}")
    public ApiResponse<PostResponseDto> updatePost(@RequestBody PostRequestDto dto, @PathVariable long id) {
        dto.setId(id);
        return ApiResponse.ok(HttpMethod.PUT, postService.updatePost(dto));
    }
}
