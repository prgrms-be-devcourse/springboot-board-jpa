package com.blackdog.springbootBoardJpa.domain.post.controller;

import com.blackdog.springbootBoardJpa.domain.post.controller.converter.PostControllerConverter;
import com.blackdog.springbootBoardJpa.domain.post.controller.dto.PostCreateDto;
import com.blackdog.springbootBoardJpa.domain.post.controller.dto.PostUpdateDto;
import com.blackdog.springbootBoardJpa.domain.post.service.PostService;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostResponse;
import com.blackdog.springbootBoardJpa.domain.post.service.dto.PostResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final PostControllerConverter controllerConverter;

    public PostController(
            final PostService postService,
            final PostControllerConverter controllerConverter
    ) {
        this.postService = postService;
        this.controllerConverter = controllerConverter;
    }

    @PostMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> savePost(@PathVariable Long userId, @Valid @RequestBody PostCreateDto createDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(postService.savePost(userId, controllerConverter.toCreateRequest(createDto)));
    }

    @PatchMapping(value = "/{postId}/user/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> updatePost(@PathVariable Long postId, @PathVariable Long userId, @Valid @RequestBody PostUpdateDto updateDto) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.updatePost(postId, userId, controllerConverter.toUpdateRequest(updateDto)));
    }

    @DeleteMapping(path = "/{postId}/user/{userId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @PathVariable Long userId) {
        postService.deletePostById(userId, postId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponses> getAllPosts(Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.findAllPosts(pageable));
    }

    @GetMapping(value = "/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> getPostById(@PathVariable Long postId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.findPostById(postId));
    }

    @GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponses> getPostsByUserId(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postService.findPostsByUserId(userId, pageable));
    }

}
