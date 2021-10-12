package com.prgrms.board.post.controller;

import com.prgrms.board.post.api.ApiResponse;
import com.prgrms.board.post.dto.PostDto;
import com.prgrms.board.post.service.PostService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
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
    public ApiResponse<PostDto> getOne(
            @PathVariable Long id
    ) throws NotFoundException {
        PostDto findPostDto = postService.findOne(id);
        return ApiResponse.ok(findPostDto);
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getAll(
            Pageable pageable
    ) {
        Page<PostDto> findPostDtos = postService.findAll(pageable);
        return ApiResponse.ok(findPostDtos);
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<Long> update(
            @RequestBody PostDto postDto
    ) throws NotFoundException {
        Long id = postService.update(postDto);
        return ApiResponse.ok(id);
    }
}
