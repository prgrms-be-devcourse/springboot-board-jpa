package com.kdt.board.post.controller;

import com.kdt.board.common.dto.ApiResponse;
import com.kdt.board.post.dto.request.PostCreateRequestDto;
import com.kdt.board.post.dto.request.PostEditRequestDto;
import com.kdt.board.post.dto.response.PostResponseDto;
import com.kdt.board.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @GetMapping("/posts")
    public ApiResponse<Page<PostResponseDto>> getAllPosts (@RequestParam int page) {
        Page<PostResponseDto> posts = postService.getAllPosts(page);
        return ApiResponse.ok(posts);
    }
    
    @GetMapping("/posts/{id}")
    public ApiResponse<PostResponseDto> getPost (@PathVariable("id") Long postId) {
        PostResponseDto post = postService.getPost(postId);
        return ApiResponse.ok(post);
    }
    
    @PostMapping("/posts")
    public ApiResponse<Long> savePost(@RequestBody PostCreateRequestDto postCreateRequestDto) {
        Long postId = postService.save(postCreateRequestDto);
        return ApiResponse.ok(postId);
    }
    
    @PutMapping("/posts")
    public ApiResponse<Long> editPost (@RequestBody PostEditRequestDto postEditRequestDto) {
        Long postId = postService.edit(postEditRequestDto);
        return ApiResponse.ok(postId);
    }
    
    @DeleteMapping("/posts/{postId}")
    public ApiResponse<Long> deletePost (@PathVariable Long postId) {
        postService.deletePost(postId);
        return ApiResponse.ok(postId);
    }
}
