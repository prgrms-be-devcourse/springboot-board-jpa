package com.prgrms.springbootboardjpa.post.presentation;

import com.prgrms.springbootboardjpa.post.application.PostService;
import com.prgrms.springbootboardjpa.post.dto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RestController
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> findOnePost(@PathVariable("postId") long postId) {
        PostResponse postResponse = postService.findById(postId);
        return ResponseEntity.ok(postResponse);
    }

    @GetMapping("/posts")
    public ResponseEntity<PostAllResponse> findAll() {
        PostAllResponse postAllResponse = postService.findAll();
        return ResponseEntity.ok(postAllResponse);
    }

    @PostMapping("/posts")
    public ResponseEntity<PostResponse> savePost(@RequestBody PostInsertRequest postInsertRequest) {
        PostResponse postResponse = postService.save(postInsertRequest);
        return ResponseEntity.created(URI.create("/posts/" + postResponse.getPostId())).body(postResponse);
    }

    @GetMapping("/posts/{pageNumber}")
    public ResponseEntity<PostOnePage> findOnePage(@PathVariable("pageNumber") int pageNumber) {
        PostOnePage onePagePost = postService.findOnePagePost(pageNumber);
        return ResponseEntity.ok(onePagePost);
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<PostResponse> updatePost(@PathVariable("postId") long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        PostResponse postResponse = postService.update(postUpdateRequest);
        return ResponseEntity.ok(postResponse);
    }

}
