package com.spring.board.springboard.post.controller;

import com.spring.board.springboard.post.domain.dto.PostCreateRequestDto;
import com.spring.board.springboard.post.domain.dto.ResponsePostDto;
import com.spring.board.springboard.post.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public ResponseEntity<List<ResponsePostDto>> getAllPosts(Pageable pageable) {
        List<ResponsePostDto> postList = postService.getAll(pageable);
        return ResponseEntity.ok(postList);
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostCreateRequestDto postCreateRequestDTO) {
        ResponsePostDto newPostDto = postService.createPost(postCreateRequestDTO);

        URI uriComponents = UriComponentsBuilder.fromUriString("/posts/{postId}")
                .buildAndExpand(
                        newPostDto.postId())
                .toUri();

        return ResponseEntity.created(uriComponents).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ResponsePostDto> getPost(@PathVariable Integer postId) {
        ResponsePostDto postResponseDTO = postService.getOne(postId);
        return ResponseEntity.ok(postResponseDTO);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<ResponsePostDto> updatePost(@PathVariable Integer postId, @RequestBody PostCreateRequestDto postCreateRequestDTO) {
        ResponsePostDto updatedPostResponseDto = postService.update(postId, postCreateRequestDTO);
        return ResponseEntity.ok(updatedPostResponseDto);
    }
}
