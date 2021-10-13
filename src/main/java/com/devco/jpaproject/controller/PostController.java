package com.devco.jpaproject.controller;

import com.devco.jpaproject.controller.dto.PostRequestDto;
import com.devco.jpaproject.controller.dto.PostResponseDto;
import com.devco.jpaproject.service.PostService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    @GetMapping("/posts")
    public ApiResponse<List<PostResponseDto>> findAll(){
        var allPosts = postService.findAll();
        return ApiResponse.ok(allPosts);
    }

    @GetMapping("/posts/pages")
    public ApiResponse<Page<PostResponseDto>> findAllByPages(Pageable pageable){
        var allPosts = postService.findAllByPages(pageable);
        return ApiResponse.ok(allPosts);
    }

    @GetMapping("/post/{id}")
    public ApiResponse<PostResponseDto> findById(@PathVariable Long id) throws NotFoundException {
        var postResponseDto = postService.findById(id);
        return ApiResponse.ok(postResponseDto);
    }

    @PostMapping("/post")
    public ApiResponse<Long> insert(@RequestBody PostRequestDto dto) throws NotFoundException {
        Long postId = postService.insert(dto);

        return ApiResponse.ok(postId);
    }

}
