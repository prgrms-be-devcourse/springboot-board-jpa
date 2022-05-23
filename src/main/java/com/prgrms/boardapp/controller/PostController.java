package com.prgrms.boardapp.controller;

import com.prgrms.boardapp.dto.PostDto;
import com.prgrms.boardapp.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private static final String SAVE_RESPONSE_KEY = "postId";
    private static final String REDIRECT_URI_PREFIX = "/posts/";

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> save(@RequestBody PostDto postDto) throws URISyntaxException {
        Long savedId = postService.save(postDto);
        Map<String, Long> body = Map.of(SAVE_RESPONSE_KEY, savedId);
        URI redirectUri = new URI(REDIRECT_URI_PREFIX + savedId);
        return ResponseEntity.created(redirectUri).body(body);
    }

    @PatchMapping
    public ResponseEntity<PostDto> update(@RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.update(postDto));
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
