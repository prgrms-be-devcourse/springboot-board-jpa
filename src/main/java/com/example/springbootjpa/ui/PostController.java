package com.example.springbootjpa.ui;


import com.example.springbootjpa.application.PostService;
import com.example.springbootjpa.ui.dto.post.PostFindResponse;
import com.example.springbootjpa.ui.dto.post.PostSaveRequest;
import com.example.springbootjpa.ui.dto.post.PostUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostFindResponse>> findAll(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok().body(postService.findAllPosts(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostFindResponse> findById(@PathVariable Long postId) {

        return ResponseEntity.ok(postService.findPost(postId));
    }

    @PostMapping
    public ResponseEntity<Map<String,Long>> createPost(@RequestBody PostSaveRequest postSaveRequest) {
        long postId = postService.createPost(postSaveRequest.userId(), postSaveRequest.title(), postSaveRequest.content());

        return ResponseEntity.created(URI.create("/api/v1/posts/" + postId)).body(Collections.singletonMap("id", postId));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Map<String, Long>> updatePost(
            @RequestBody PostUpdateRequest postUpdateRequest,
            @PathVariable Long postId
    ) {
        postService.updatePost(postId, postUpdateRequest.title(), postUpdateRequest.content());

        return ResponseEntity.ok().body(Collections.singletonMap("id", postId));
    }
}
