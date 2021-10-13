package com.eunu.springbootboard.controller;

import com.eunu.springbootboard.ApiResponse;
import com.eunu.springbootboard.domain.post.PostDto;
import com.eunu.springbootboard.domain.post.PostService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {
    @Autowired
    PostService postService;

    @GetMapping("/posts/{postId}")
    public ApiResponse<PostDto> getOne(@PathVariable Long postId) throws NotFoundException {
        PostDto one = postService.findOne(postId);
        return ApiResponse.ok(one);
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable) {
        Page<PostDto> all = postService.findAll(pageable);
        return ApiResponse.ok(all);
    }

    @PostMapping("/posts")
    public ApiResponse<Long> save(@RequestBody PostDto postDto) {
        Long postId = postService.save(postDto);
        return ApiResponse.ok(postId);
    }

    @PostMapping("/posts/{postId}")
    public ApiResponse<PostDto> update(@PathVariable Long postId, @RequestBody PostDto postDto) throws NotFoundException {
        PostDto one = postService.update(postId, postDto);
        return ApiResponse.ok(one);
    }


}
