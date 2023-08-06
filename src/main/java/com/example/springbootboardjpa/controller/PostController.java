package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.dto.post.request.PostCreateRequest;
import com.example.springbootboardjpa.dto.post.request.PostUpdateRequest;
import com.example.springbootboardjpa.dto.post.response.PostResponse;
import com.example.springbootboardjpa.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PostResponse createPost(@RequestBody PostCreateRequest createRequest) {
        return postService.createPost(createRequest);
    }

    @GetMapping
    public List<PostResponse> findPostAll() {
        return postService.findAllPosts();
    }

    @GetMapping("/{id}")
    public PostResponse findPostById(@PathVariable Long id) {
        return postService.findPostById(id);
    }

    @PatchMapping("/{id}")
    public PostResponse updatePost(@PathVariable Long id, @RequestBody PostUpdateRequest updateRequest) {
        return postService.updatePost(id, updateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePostById(@PathVariable Long id) {
        postService.deletePostById(id);
    }
}
