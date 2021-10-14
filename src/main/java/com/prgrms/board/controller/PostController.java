package com.prgrms.board.controller;

import com.prgrms.board.dto.post.PostCreateRequest;
import com.prgrms.board.dto.post.PostFindResponse;
import com.prgrms.board.dto.post.PostModifyRequest;
import com.prgrms.board.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Long> createPost(@Valid @RequestBody PostCreateRequest postCreateRequest){
        return ResponseEntity.ok(postService.createPost(postCreateRequest));
    }

    @GetMapping
    public ResponseEntity<PostFindResponse> findPost(final @RequestParam Long postId){
        return ResponseEntity.ok(postService.findPost(postId));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<PostFindResponse>> findAllPostByUserId(Pageable pageable, final @RequestParam Long userId) {
        return ResponseEntity.ok(postService.findAllPostByUserId(pageable, userId));
    }

    @PutMapping
    public ResponseEntity<Long> modifyPost(final @Valid @RequestBody PostModifyRequest postModifyRequest){
        return ResponseEntity.ok(postService.modifyPost(postModifyRequest));
    }

    @DeleteMapping
    public ResponseEntity<Long> removePost(final @RequestParam Long postId){
        return ResponseEntity.ok(postService.removePost(postId));
    }
}
