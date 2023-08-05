package com.juwoong.springbootboardjpa.post.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.juwoong.springbootboardjpa.post.api.model.PostRequest;
import com.juwoong.springbootboardjpa.post.application.PostService;
import com.juwoong.springbootboardjpa.post.application.model.PostDto;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    private PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<PostDto> createPost(@PathVariable Long id,
        @RequestBody PostRequest request) {
        PostDto post = postService.createPost(id, request.title(), request.content());

        return ResponseEntity.ok(post);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPost(@PathVariable Long id) {
        PostDto post = postService.getPost(id);

        return ResponseEntity.ok(post);
    }

    @GetMapping()
    public ResponseEntity<Page<PostDto>> getAllPosts(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<PostDto> posts = postService.getAllPosts(pageRequest);

        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id,
        @RequestBody PostRequest request) {
        PostDto post = postService.updatePost(id, request.title(), request.content());

        return ResponseEntity.ok(post);
    }

}
