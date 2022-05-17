package com.prgrms.springbootboardjpa.controller;


import com.prgrms.springbootboardjpa.dto.ApiResponse;
import com.prgrms.springbootboardjpa.dto.PostDto;
import com.prgrms.springbootboardjpa.service.PostService;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundExceptionHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> exceptionHandler(Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @PostMapping("/posts")
    public ApiResponse<Long> createPost(@RequestBody PostDto postDto) {
        Long save = postService.save(postDto);
        return ApiResponse.ok(save);
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> getAllPost(Pageable pageable) {
        Page<PostDto> all = postService.findAllPosts(pageable);
        return ApiResponse.ok(all);
    }

    @GetMapping("/posts/{postId}")
    public ApiResponse<PostDto> getOnePost(@PathVariable Long postId) throws NotFoundException {
        PostDto one = postService.findById(postId);
        return ApiResponse.ok(one);
    }

    @PatchMapping("/posts/{postId}")
    public ApiResponse<Long> updatePostContent(@PathVariable Long postId,
        @RequestBody String content) throws NotFoundException {
        Long update = postService.updateContent(postId, content);
        return ApiResponse.ok(update);
    }


}
