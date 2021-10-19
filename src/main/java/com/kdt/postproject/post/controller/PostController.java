package com.kdt.postproject.post.controller;

import com.kdt.postproject.ApiResponse;
import com.kdt.postproject.post.dto.PostDto;
import com.kdt.postproject.post.service.PostService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler (NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler (Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }
    //    - 게시글 조회
    //    -- 페이징 조회 (GET "/posts")
    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable){
        Page<PostDto> all = postService.findAll(pageable);
        return ApiResponse.ok(all);
    }
    //    -- 단건 조회 (GET "/posts/{id}")
    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getOne(@PathVariable Long id) throws NotFoundException{
        PostDto one = postService.findOne(id);
        return ApiResponse.ok(one);
    }

    //    - 게시글 작성 (POST "/posts")
    @PostMapping("/posts")
    public ApiResponse<PostDto> save(@RequestBody PostDto postDto){
        Long id = postService.save(postDto);
        return ApiResponse.ok(postDto);
    }

    //   - 게시글 수정 (POST "/posts/{id}")
    @PostMapping("/posts/{id}")
    public ApiResponse<PostDto> update(@RequestBody PostDto postDto) throws NotFoundException{
        PostDto updated = postService.update(postDto);
        return ApiResponse.ok(updated);
    }
}
