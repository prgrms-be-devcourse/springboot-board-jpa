package com.example.kdtboard.controller;

import com.example.kdtboard.dto.ApiResponse;
import com.example.kdtboard.dto.CreatePostRequest;
import com.example.kdtboard.dto.PostDto;
import com.example.kdtboard.dto.UpdatePostRequest;
import com.example.kdtboard.service.PostService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e)  {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e)  {
        return ApiResponse.fail(500, e.getMessage());
    }


    @PostMapping
    public ApiResponse<Long> save(@RequestBody CreatePostRequest createPostRequest) throws NotFoundException {
        Long saveId = postService.save(createPostRequest);
        return ApiResponse.ok(saveId);
    }
    @GetMapping
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable){
        Page<PostDto> page = postService.findAll(pageable);
        return ApiResponse.ok(page);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto> getOne(@PathVariable Long id) throws NotFoundException {
        PostDto findPost = postService.findOne(id);
        return ApiResponse.ok(findPost);
    }

    @PatchMapping
    public ApiResponse<Long> update(@RequestBody UpdatePostRequest updatePostRequest) throws NotFoundException {
        Long updateOne = postService.updateOne(updatePostRequest);
        return ApiResponse.ok(updateOne);
    }

}
