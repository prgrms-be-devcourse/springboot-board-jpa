package org.prgrms.springbootboard.controller;

import java.net.URI;

import javax.validation.Valid;

import org.prgrms.springbootboard.dto.PostCreateRequest;
import org.prgrms.springbootboard.dto.PostResponse;
import org.prgrms.springbootboard.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/posts")
@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<PostResponse> createPost(@RequestBody @Valid PostCreateRequest request) {
        PostResponse response = postService.createPost(request);
        return ResponseEntity.created(URI.create("/posts/" + response.getId()))
            .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        PostResponse response = postService.findById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponse>> findAll(Pageable pageable) {
        Page<PostResponse> responses = postService.findAll(pageable);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{writerId}")
    public ResponseEntity<Page<PostResponse>> findAllByWriter(@PathVariable Long writerId, Pageable pageable) {
        Page<PostResponse> responses = postService.findAllByWriter(writerId, pageable);
        return ResponseEntity.ok(responses);
    }
}
