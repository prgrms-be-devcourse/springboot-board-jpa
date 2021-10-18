package com.kdt.bulletinboard.controller;

import com.kdt.bulletinboard.ApiResponse;
import com.kdt.bulletinboard.dto.PostDto;
import com.kdt.bulletinboard.service.PostService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/posts")
    public ApiResponse<Long> save(@RequestBody PostDto postDto) {
        Long id = postService.save(postDto);
        return ApiResponse.ok(id);
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> findOnePost(@PathVariable Long id) throws NotFoundException {
        PostDto foundPost = postService.findOnePost(id);
        return ApiResponse.ok(foundPost);
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> findAllPost(Pageable pageable) {
        return ApiResponse.ok(postService.findAllPost(pageable));
    }

    @ExceptionHandler
    private ApiResponse<String> exceptionHandle(Exception exception) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    private ApiResponse<String> notFoundHandle(NotFoundException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }
}
