package com.prgrms.board.post.controller;

import com.prgrms.board.common.dto.PageDto;
import com.prgrms.board.post.api.ApiResponse;
import com.prgrms.board.post.dto.PostDto;
import com.prgrms.board.post.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ApiResponse<Long> save(@RequestBody @Valid PostDto postDto) {
        Long id = postService.save(postDto);
        return ApiResponse.ok(id);
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> getOne(@PathVariable Long id) {
        PostDto findPostDto = postService.findOne(id);
        return ApiResponse.ok(findPostDto);
    }

    @GetMapping("/posts")
    public ApiResponse<PageDto<PostDto>> getAll(Pageable pageable) {
        PageDto<PostDto> findPostDtos = postService.findAll(pageable);
        return ApiResponse.ok(findPostDtos);
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<Long> update(@PathVariable Long id, @RequestBody @Valid PostDto postDto) {
        Long updatedPostId = postService.update(id, postDto);
        return ApiResponse.ok(updatedPostId);
    }
}
