package com.example.springbootjpa.ui;


import com.example.springbootjpa.application.PostService;
import com.example.springbootjpa.ui.dto.post.PostDto;
import com.example.springbootjpa.ui.dto.post.PostSaveRequestDto;
import com.example.springbootjpa.ui.dto.post.PostUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostDto>> findAll(@PageableDefault(size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {

        return ResponseEntity.ok().body(postService.findAllPosts(pageable));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findById(@PathVariable Long postId) {

        return ResponseEntity.ok(postService.findPost(postId));
    }

    @PostMapping
    public ResponseEntity<Map<String,Long>> createPost(@RequestBody PostSaveRequestDto postSaveRequestDto) {
        long postId = postService.createPost(postSaveRequestDto.userId(), postSaveRequestDto.title(), postSaveRequestDto.content());

        return ResponseEntity.created(URI.create("/api/v1/posts/" + postId)).body(Collections.singletonMap("id", postId));
    }

    @PatchMapping("/{postId}")
    public ResponseEntity<Map<String, Long>> updatePost(
            @RequestBody PostUpdateRequestDto postUpdateRequestDto,
            @PathVariable Long postId
    ) {
        postService.updatePost(postId, postUpdateRequestDto.title(), postUpdateRequestDto.content());

        return ResponseEntity.ok().body(Collections.singletonMap("id", postId));
    }
}
