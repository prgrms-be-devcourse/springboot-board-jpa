package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.dto.PostDto;
import com.example.springbootboardjpa.response.ApiResponse;
import com.example.springbootboardjpa.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/posts")
public class PostRestController {
    @Autowired
    PostService postService;

    @ExceptionHandler(value = {RuntimeException.class})
    public ApiResponse<String> error(Exception e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Long> create(@RequestBody PostDto postDto) {
        long result = postService.create(postDto);
        return ApiResponse.ok(result);
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> update1(@PathVariable long id, @RequestBody PostDto postDto) {
        String result = postService.update(id, postDto);
        return ApiResponse.ok(result);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<Page<PostDto>> get(Pageable pageable) {
        Page<PostDto> result = postService.readAll(pageable);
        return ApiResponse.ok(result);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<PostDto> get(@PathVariable Long id) {
        PostDto result = postService.read(id);
        return ApiResponse.ok(result);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<String> delete(@PathVariable Long id) {
        String result = postService.delete(id);
        return ApiResponse.ok(result);
    }
}
