package com.prgms.jpaBoard.domain.post.presentation;


import com.prgms.jpaBoard.domain.post.application.PostService;
import com.prgms.jpaBoard.domain.post.application.dto.PostResponse;
import com.prgms.jpaBoard.domain.post.application.dto.PostResponses;
import com.prgms.jpaBoard.domain.post.presentation.dto.PostSaveRequest;
import com.prgms.jpaBoard.domain.post.presentation.dto.PostUpdateRequest;
import com.prgms.jpaBoard.global.ApiResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<PostResponses>> readPosts(
            @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        PostResponses postResponses = postService.readAllPost(pageable);
        return ResponseEntity.ok(ApiResponse.ok(postResponses));
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> readPost(@PathVariable Long id) {
        PostResponse postResponse = postService.readPost(id);
        return ResponseEntity.ok(ApiResponse.ok(postResponse));
    }

    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<Map<String, Long>>> post(@RequestBody PostSaveRequest postSaveRequest) {
        Long postId = postService.post(postSaveRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse
                        .success(HttpStatus.CREATED.value(), Collections.singletonMap("id", postId)));
    }

    @PostMapping("/posts/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> updatePost(
            @PathVariable Long id,
            @RequestBody PostUpdateRequest postUpdateRequest)
    {
        PostResponse postResponse = postService.updatePost(id, postUpdateRequest);
        return ResponseEntity.ok(ApiResponse.ok(postResponse));
    }

}
