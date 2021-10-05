package com.devcourse.bbs.controller;

import com.devcourse.bbs.controller.bind.ApiResponse;
import com.devcourse.bbs.domain.user.Post;
import com.devcourse.bbs.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Post>>> getPostsByPage
            (@RequestParam(name = "page") int page,
             @RequestParam(name = "size") int size) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> getPost(@PathVariable(name = "id") long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Post>> createPost(@RequestBody Object request) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Post>> updatePost(@RequestBody Object request) {
        return null;
    }
}
