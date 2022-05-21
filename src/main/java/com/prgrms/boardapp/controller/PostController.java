package com.prgrms.boardapp.controller;

import com.prgrms.boardapp.dto.PostDto;
import com.prgrms.boardapp.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    private static final String SAVE_RESPONSE_KEY = "postId";

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> save(@RequestBody PostDto postDto) {
        Long savedId = postService.save(postDto);
        Map<String, Long> body = Map.of(SAVE_RESPONSE_KEY, savedId);
        return ResponseEntity.ok(body);
    }

    @GetMapping
    public ResponseEntity<Page<PostDto>> findAll(Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findById(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.findById(postId));
    }
}
