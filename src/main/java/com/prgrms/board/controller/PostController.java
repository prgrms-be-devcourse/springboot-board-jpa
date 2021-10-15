package com.prgrms.board.controller;

import com.prgrms.board.dto.IdResponse;
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
    public ResponseEntity<IdResponse> createPost(@Valid @RequestBody PostCreateRequest postCreateRequest){
        return ResponseEntity.ok(postService.createPost(postCreateRequest));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostFindResponse> findPost(final @PathVariable Long postId){
        return ResponseEntity.ok(postService.findPost(postId));
    }

    @GetMapping("/list")
    public ResponseEntity<Page<PostFindResponse>> findAllPostByUserId(Pageable pageable, final @RequestBody Long userId) {
        return ResponseEntity.ok(postService.findAllPostByUserId(pageable, userId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<IdResponse> modifyPost(final @PathVariable Long postId, final @Valid @RequestBody PostModifyRequest postModifyRequest){
        return ResponseEntity.ok(postService.modifyPost(postId, postModifyRequest));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<IdResponse> removePost(final @PathVariable Long postId){
        return ResponseEntity.ok(postService.removePost(postId));
    }
}
