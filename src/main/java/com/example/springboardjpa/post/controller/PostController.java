package com.example.springboardjpa.post.controller;

import com.example.springboardjpa.post.dto.PostDto;
import com.example.springboardjpa.post.response.ApiResponse;
import com.example.springboardjpa.post.service.PostService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(Exception e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @PostMapping("/posts")
    public ApiResponse<Long> save(@RequestBody PostDto postDto) {
        long id = postService.save(postDto);
        return ApiResponse.ok(id);
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getOne(@PathVariable Long id) throws NotFoundException{
        PostDto one = postService.findOne(id);
        return ApiResponse.ok(one);
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable){
        Page<PostDto> all = postService.findAll(pageable);
        return ApiResponse.ok(all);
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<PostDto> updateOne(@PathVariable Long id,
                          @RequestBody PostDto postDto) throws NotFoundException{
        postService.update(postDto, id);
        return ApiResponse.ok(postDto);
    }

}
