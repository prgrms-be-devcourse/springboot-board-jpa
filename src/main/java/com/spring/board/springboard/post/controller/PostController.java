package com.spring.board.springboard.post.controller;

import com.spring.board.springboard.post.domain.dto.PostCreateRequestDto;
import com.spring.board.springboard.post.domain.dto.PostSummaryResponseDto;
import com.spring.board.springboard.post.domain.dto.PostDetailResponseDto;
import com.spring.board.springboard.post.service.PostService;
import jakarta.validation.Valid;
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
    public ResponseEntity<List<PostSummaryResponseDto>> getAllPosts(Pageable pageable) {
        final List<PostSummaryResponseDto> postList = postService.getAll(pageable);
        return ResponseEntity.ok(postList);
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@Valid @RequestBody PostCreateRequestDto postCreateRequestDto) {
        final PostDetailResponseDto newPostDto = postService.createPost(postCreateRequestDto);

        final URI uriComponents = UriComponentsBuilder.fromUriString("/posts/{postId}")
                .buildAndExpand(
                        newPostDto.postId())
                .toUri();

        return ResponseEntity.created(uriComponents).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> getPost(@PathVariable Integer postId) {
        final PostDetailResponseDto postResponseDTO = postService.getOne(postId);
        return ResponseEntity.ok(postResponseDTO);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<PostDetailResponseDto> updatePost(@PathVariable Integer postId,
                                                            @Valid @RequestBody PostCreateRequestDto postCreateRequestDTO) {
        final PostDetailResponseDto updatedPostResponseDto = postService.update(postId, postCreateRequestDTO);
        return ResponseEntity.ok(updatedPostResponseDto);
    }
}
