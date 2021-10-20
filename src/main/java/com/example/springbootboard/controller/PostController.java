package com.example.springbootboard.controller;

import com.example.springbootboard.controller.api.ApiResponse;
import com.example.springbootboard.dto.PostRequestDto;
import com.example.springbootboard.dto.PostResponseDto;
import com.example.springbootboard.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController()
public class PostController {

    @Autowired
    private PostService postService;

    @PostMapping()
    public ApiResponse<Long> insert(@Valid @RequestBody PostRequestDto postRequestDto) {
        Long id = postService.insert(postRequestDto);
        return ApiResponse.ok(id);
    }

    @GetMapping("/api/v1/posts/{id}")
    public ApiResponse<PostResponseDto> getOne(@PathVariable(value = "id") Long id) {
        PostResponseDto postResponseDto = postService.findById(id);
        return ApiResponse.ok(postResponseDto);
    }

    @GetMapping("/api/v1/posts")
    public ApiResponse<List<PostResponseDto>> getAll() {
        List<PostResponseDto> all = postService.findAll();
        return ApiResponse.ok(all);
    }

    @PatchMapping("/api/v1/posts/{id}")
    public ApiResponse<PostResponseDto> update(
            @PathVariable(value = "id") Long id,
            @RequestBody PostRequestDto postRequestDto
    ) {
        PostResponseDto postResponseUpdatedDto = postService.update(id, postRequestDto);
        return ApiResponse.ok(postResponseUpdatedDto);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public ApiResponse<String> delete(@PathVariable(value = "id") Long id) {
        postService.delete(id);
        return ApiResponse.ok("게시글 삭제 성공");
    }
}
