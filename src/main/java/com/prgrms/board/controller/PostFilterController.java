package com.prgrms.board.controller;

import com.prgrms.board.dto.post.PostFindResponse;
import com.prgrms.board.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostFilterController {

    private final PostService postService;

    public PostFilterController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/users/{id}/posts")
    public ResponseEntity<Page<PostFindResponse>> findAllPostByUserId(Pageable pageable, final @PathVariable Long id) {
        return ResponseEntity.ok(postService.findAllPostByUserId(pageable, id));
    }

}
