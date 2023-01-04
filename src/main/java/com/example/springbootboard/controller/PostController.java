package com.example.springbootboard.controller;

import com.example.springbootboard.dto.ApiResponse;
import com.example.springbootboard.dto.PostCreateRequest;
import com.example.springbootboard.dto.PostDto;
import com.example.springbootboard.facade.PostFacade;
import com.example.springbootboard.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.example.springbootboard.dto.Converter.*;
@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    private final PostFacade postFacade;

    public PostController(PostService postService, PostFacade postFacade) {
        this.postService = postService;
        this.postFacade = postFacade;
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable){
        Page<PostDto> all = postService.findAll(pageable);
        return ApiResponse.ok(all);
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getOneByPostId(@PathVariable Long id) throws Exception {
        PostDto postDto = postService.findPostById(id);
        return ApiResponse.ok(postDto);
    }

    @PostMapping("/posts")
    public ApiResponse<PostDto> savePost(@RequestBody @Valid PostCreateRequest request) throws Exception {
        PostDto dto = postToDto(postFacade.createPost(request));
        return ApiResponse.ok(dto);
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<PostDto> updatePost(@RequestBody @Valid PostDto postDto) throws Exception{
        PostDto dto = postService.updatePost(postDto);
        return ApiResponse.ok(dto);
    }
}
