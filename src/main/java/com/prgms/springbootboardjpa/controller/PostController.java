package com.prgms.springbootboardjpa.controller;

import com.prgms.springbootboardjpa.dto.ApiResponse;
import com.prgms.springbootboardjpa.dto.CreatePostRequest;
import com.prgms.springbootboardjpa.dto.PostDto;
import com.prgms.springbootboardjpa.dto.UpdatePostRequest;
import com.prgms.springbootboardjpa.facade.PostFacade;
import com.prgms.springbootboardjpa.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private final PostFacade postFacade;
    private final PostService postService;

    @PostMapping("/posts")
    @ResponseStatus(CREATED)
    public ApiResponse<Long> createPost(
            @RequestBody @Validated CreatePostRequest createPostRequest
    ) {
        return new ApiResponse<>(postFacade.createPost(createPostRequest));
    }

    @PostMapping("/posts/{postId}")
    public ApiResponse<Long> updatePost(
            @PathVariable Long postId,
            @RequestBody @Validated UpdatePostRequest updatePostRequest
    ) {
        return new ApiResponse<>(postService.updatePost(postId, updatePostRequest));
    }

    @GetMapping("/posts/{postId}")
    public ApiResponse<PostDto> getPost(@PathVariable Long postId) {
        log.info(Thread.currentThread().getName() + "------" + postId);
        log.info(String.valueOf(this.getClass()));
        return new ApiResponse<>(postService.getPost(postId));
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getPostList(Pageable pageable) {
        return new ApiResponse<>(postService.getPostList(pageable));
    }
}
