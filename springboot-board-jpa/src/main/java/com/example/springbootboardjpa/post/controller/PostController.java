package com.example.springbootboardjpa.post.controller;

import com.example.springbootboardjpa.post.dto.CreatePostRequest;
import com.example.springbootboardjpa.post.dto.PostDto;
import com.example.springbootboardjpa.post.service.PostService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {
    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> notFoundHandler() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> internalServerErrorHandler(Exception e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @PutMapping
    public ResponseEntity<PostDto> insert(@RequestBody CreatePostRequest createPostRequest) {
        PostDto save = postService.insert(createPostRequest);
        return ResponseEntity.created(URI.create("/api/v1/posts/" + save.getId())).body(save);
    }

    @GetMapping
    public ResponseEntity<Page<PostDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getById(@PathVariable Long id)
            throws NotFoundException {
        return ResponseEntity.ok(postService.findById(id));
    }

    @PatchMapping
    public ResponseEntity<PostDto> modify(@RequestBody PostDto postDto) {
        PostDto update = postService.update(postDto);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Object> deleteById(@PathVariable Long id) {
        postService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
