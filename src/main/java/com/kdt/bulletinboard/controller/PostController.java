package com.kdt.bulletinboard.controller;

import com.kdt.bulletinboard.ApiResponse;
import com.kdt.bulletinboard.dto.PostDto;
import com.kdt.bulletinboard.service.PostService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping(value = "/posts")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ApiResponse<Long> write(@Valid @RequestBody PostDto postDto) {
        Long id = postService.save(postDto);
        return ApiResponse.ok(id);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto> findOnePost(@PathVariable Long id) throws NotFoundException {
        PostDto foundPost = postService.findOnePost(id);
        return ApiResponse.ok(foundPost);
    }

    @GetMapping
    public ApiResponse<Page<PostDto>> findAllPost(Pageable pageable) {
        return ApiResponse.ok(postService.findAllPost(pageable));
    }

    @PutMapping("/{id}")
    public ApiResponse<Long> updatePost(@PathVariable Long id, @Valid @RequestBody PostDto postDto) throws NotFoundException {
        return ApiResponse.ok(postService.updatePost(id, postDto));
    }

}
