package com.spring.board.springboard.post.controller;

import com.spring.board.springboard.domain.Response;
import com.spring.board.springboard.post.domain.dto.RequestPostDto;
import com.spring.board.springboard.post.domain.dto.ResponsePostDto;
import com.spring.board.springboard.post.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Response<List<ResponsePostDto>>> getAllPosts(Pageable pageable) {
        List<ResponsePostDto> postList = postService.getAll(pageable);
        Response<List<ResponsePostDto>> response = new Response<>(postList);
        if (postList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody RequestPostDto requestPostDTO) {
        ResponsePostDto newPostDto = postService.createPost(requestPostDTO);

        URI uriComponents = UriComponentsBuilder.fromUriString("/posts/{postId}")
                .buildAndExpand(
                        newPostDto.postId())
                .toUri();

        return ResponseEntity.created(uriComponents).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Response<ResponsePostDto>> getPost(@PathVariable Integer postId) {
        ResponsePostDto postResponseDTO = postService.getOne(postId);
        Response<ResponsePostDto> response = new Response<>(postResponseDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Response<ResponsePostDto>> updatePost(@PathVariable Integer postId, @RequestBody RequestPostDto requestPostDTO) {
        ResponsePostDto updatedPostResponseDto = postService.update(postId, requestPostDTO);
        Response<ResponsePostDto> response = new Response<>(updatedPostResponseDto);
        return ResponseEntity.ok(response);
    }
}
