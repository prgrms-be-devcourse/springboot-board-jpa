package com.prgrms.boardjpa.Post.presentation;

import com.prgrms.boardjpa.Post.application.PostService;
import com.prgrms.boardjpa.Post.dto.request.PostCreateRequest;
import com.prgrms.boardjpa.Post.dto.request.PostUpdateRequest;
import com.prgrms.boardjpa.Post.dto.response.PostListResponse;
import com.prgrms.boardjpa.Post.dto.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostApiController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponse> create(@Validated @RequestBody PostCreateRequest createRequest) {
        return ResponseEntity.ok(postService.create(createRequest));
    }

    @GetMapping
    public ResponseEntity<PostListResponse> findAll(@PageableDefault(size = 5, sort = "id",
            direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findOne(@PathVariable("id") Long postId) {
        return ResponseEntity.ok(postService.findOne(postId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PostResponse> update(@PathVariable("id") Long postId, @RequestBody PostUpdateRequest updateRequest) {
        return ResponseEntity.ok(postService.update(postId, updateRequest));
    }
}
