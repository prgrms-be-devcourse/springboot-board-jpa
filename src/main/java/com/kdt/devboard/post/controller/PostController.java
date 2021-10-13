package com.kdt.devboard.post.controller;

import com.kdt.devboard.Common.ApiResponse;
import com.kdt.devboard.post.domain.Post;
import com.kdt.devboard.post.domain.PostDto;
import com.kdt.devboard.post.service.PostService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> exceptionHandler(Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @PostMapping
    public ApiResponse<Long> save(@RequestBody PostDto postDto) {
        return ApiResponse.ok(postService.save(postDto));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto> getOne(@PathVariable Long id) throws NotFoundException {
        return ApiResponse.ok(postService.findOne(id));
    }

    @GetMapping
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable) {
        return ApiResponse.ok(postService.findAll(pageable));
    }

    @PutMapping
    public ApiResponse<PostDto> updateOne(@RequestBody PostDto postDto) throws NotFoundException {
        return ApiResponse.ok(postService.update(postDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> removeOne(@PathVariable Long id) throws NotFoundException {
        postService.deleteById(id);
        return ApiResponse.ok("delete complete");
    }

}
