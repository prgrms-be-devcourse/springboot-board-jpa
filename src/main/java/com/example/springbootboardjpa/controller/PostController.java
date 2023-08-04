package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.dto.request.PostCreateRequest;
import com.example.springbootboardjpa.dto.request.PostUpdateRequest;
import com.example.springbootboardjpa.dto.response.PostResponse;
import com.example.springbootboardjpa.service.PostService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
