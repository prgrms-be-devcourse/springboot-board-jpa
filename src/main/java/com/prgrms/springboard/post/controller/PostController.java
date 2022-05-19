package com.prgrms.springboard.post.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.springboard.global.common.ApiResponse;
import com.prgrms.springboard.post.dto.CreatePostRequest;
import com.prgrms.springboard.post.dto.ModifyPostRequest;
import com.prgrms.springboard.post.dto.PageRequest;
import com.prgrms.springboard.post.dto.PostResponse;
import com.prgrms.springboard.post.dto.PostsResponse;
import com.prgrms.springboard.post.service.PostService;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    private static final String MODIFY_COMPLETED = "정상적으로 수정되었습니다.";

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PostsResponse> showPosts(PageRequest pageRequest) {
        PostsResponse posts = postService.findAll(pageRequest.toPageable());
        return ApiResponse.ok(posts);
    }

    @GetMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PostResponse> showPost(@PathVariable Long id) {
        PostResponse post = postService.findOne(id);
        return ApiResponse.ok(post);
    }

    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<Long>> createPost(@Valid @RequestBody CreatePostRequest postRequest) {
        Long id = postService.createPost(postRequest);
        return ResponseEntity.created(URI.create("/api/v1/posts/" + id)).body(ApiResponse.ok(id));
    }

    @PutMapping("/posts/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<String> modifyPost(@PathVariable Long id, @Valid @RequestBody ModifyPostRequest postRequest) {
        postService.modifyPost(id, postRequest);
        return ApiResponse.ok(MODIFY_COMPLETED);
    }

}
