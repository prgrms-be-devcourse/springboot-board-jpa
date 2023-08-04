package com.juwoong.springbootboardjpa.post.api;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping()
    public ResponseEntity<PostDto> createPost(@RequestParam Long userId,
        @RequestBody PostRequest request) {
        PostDto post = postService.createPost(userId, request.title(), request.content());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(post, headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable Long id) {
        PostDto post = postService.getPostById(id);

        return ResponseEntity.ok(post);
    }

    @GetMapping()
    public Page<PostDto> getAllPosts(@RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "2") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        return postService.getAllPosts(pageRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id,
        @RequestBody PostRequest request) {
        PostDto post = postService.updatePost(id, request.title(), request.content());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(post, headers, HttpStatus.OK);
    }

}
