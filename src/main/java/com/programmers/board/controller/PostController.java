package com.programmers.board.controller;

import com.programmers.board.dto.PostDto;
import com.programmers.board.dto.request.PostCreateRequest;
import com.programmers.board.dto.request.PostUpdateRequest;
import com.programmers.board.dto.request.PostsGetRequest;
import com.programmers.board.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts")
    public PageResult<PostDto> findPosts(@ModelAttribute PostsGetRequest request) {
        Page<PostDto> posts = postService.findPosts(
                request.getPage(),
                request.getSize());
        return new PageResult<>(posts);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public Result<Long> createPost(@RequestBody PostCreateRequest request) {
        Long postId = postService.createPost(
                request.getUserId(),
                request.getTitle(),
                request.getContent());
        return new Result<>(postId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts/{postId}")
    public Result<PostDto> findPost(@PathVariable("postId") Long postId) {
        PostDto post = postService.findPost(postId);
        return new Result<>(post);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/posts/{postId}")
    public void updatePost(@PathVariable("postId") Long postId,
                           @RequestBody PostUpdateRequest request) {
        postService.updatePost(
                postId,
                request.getTitle(),
                request.getContent());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable("postId") Long postId) {
        postService.deletePost(postId);
    }
}
