package com.prgrms.board.controller;

import com.prgrms.board.dto.IdResponse;
import com.prgrms.board.dto.post.PostCreateRequest;
import com.prgrms.board.dto.post.PostFindResponse;
import com.prgrms.board.dto.post.PostModifyRequest;
import com.prgrms.board.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Validated
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

    @GetMapping("/{id}")
    public ResponseEntity<PostFindResponse> findPost(final @PathVariable @Positive Long id){
        return ResponseEntity.ok(postService.findPost(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<IdResponse> modifyPost(final @PathVariable Long id, final @Valid @RequestBody PostModifyRequest postModifyRequest){
        return ResponseEntity.ok(postService.modifyPost(id, postModifyRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<IdResponse> removePost(final @PathVariable @Positive Long id){
        return ResponseEntity.ok(postService.removePost(id));
    }
}
