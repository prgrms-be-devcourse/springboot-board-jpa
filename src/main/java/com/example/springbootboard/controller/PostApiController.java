package com.example.springbootboard.controller;

import com.example.springbootboard.dto.*;
import com.example.springbootboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.net.URI;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RequestMapping(value = "/posts", produces = MediaType.APPLICATION_JSON_VALUE+";charset=UTF-8")
@RestController
public class PostApiController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Long> save(@Valid @RequestBody final RequestCreatePost request) {
        Long postId = postService.save(request);
        return ResponseEntity.created(URI.create("/posts/" + postId)).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponsePost> getOne(@PathVariable("postId") final Long postId) {
        ResponsePost post = postService.findOne(postId);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public ResponseEntity<ResponsePagePost> getAll(final RequestPagePost pageable) {
        ResponsePagePost posts = postService.findAll(pageable.of());

        return ResponseEntity.ok(posts);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<Void> update(@PathVariable("postId") final Long postId, @Valid @RequestBody final RequestUpdatePost request) {
        postService.update(postId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> delete(@PathVariable("postId") final Long postId) {
        postService.delete(postId);
        return ResponseEntity.noContent().build();
    }
}
