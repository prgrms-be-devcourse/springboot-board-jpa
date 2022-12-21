package com.spring.board.springboard.post.controller;

import com.spring.board.springboard.domain.Response;
import com.spring.board.springboard.post.domain.dto.RequestPostDTO;
import com.spring.board.springboard.post.domain.dto.ResponsePostDTO;
import com.spring.board.springboard.post.domain.dto.UpdatePostDTO;
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
    public ResponseEntity<Response<List<ResponsePostDTO>>> getAllPosts(Pageable pageable) {
        List<ResponsePostDTO> postList = postService.getAll(pageable);
        Response<List<ResponsePostDTO>> response = new Response<>(postList);
        if (postList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody RequestPostDTO requestPostDTO) {
        ResponsePostDTO newPostDTO = postService.createPost(requestPostDTO);

        URI uriComponents = UriComponentsBuilder.fromUriString("/posts/{postId}")
                .buildAndExpand(
                        newPostDTO.getPostId())
                .toUri();

        return ResponseEntity.created(uriComponents).build();
    }

    @GetMapping("/{postId}")
    public ResponseEntity<Response<ResponsePostDTO>> getPost(@PathVariable Integer postId) {
        ResponsePostDTO postResponseDTO = postService.getOne(postId);
        Response<ResponsePostDTO> response = new Response<>(postResponseDTO);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{postId}")
    public ResponseEntity<Response<ResponsePostDTO>> updatePost(@PathVariable Integer postId, @RequestBody UpdatePostDTO updatePostDTO) {
        ResponsePostDTO updatedPostResponseDTO = postService.update(postId, updatePostDTO);
        Response<ResponsePostDTO> response = new Response<>(updatedPostResponseDTO);
        return ResponseEntity.ok(response);
    }
}
