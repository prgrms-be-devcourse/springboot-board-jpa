package com.example.springbootboard.controller;

import com.example.springbootboard.converter.PostConverter;
import com.example.springbootboard.domain.Post;
import com.example.springbootboard.dto.CreatePostRequestDto;
import com.example.springbootboard.dto.PostDto;
import com.example.springbootboard.dto.UpdatePostRequestDto;
import com.example.springbootboard.service.PostService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    private final PostService postService;
    private final PostConverter postConverter;

    public PostController(PostService postService, PostConverter postConverter) {
        this.postService = postService;
        this.postConverter = postConverter;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> notFoundHandler(NotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostDto> getOnePost(
            @PathVariable("id") Long id
    ) throws NotFoundException {
        Post post = postService.findOne(id);
        PostDto postDto = new PostDto(post.getId(), post.getTitle(), post.getContent(), post.getUser());
        return ResponseEntity.ok(postDto);
    }

    @GetMapping("/posts")
    public ResponseEntity<Page<PostDto>> getAllPost(Pageable pageable) {
        Page<Post> all = postService.findAll(pageable);
        return ResponseEntity.ok(all.map(postConverter::convertPost));
    }

    @PostMapping("/posts")
    public ResponseEntity<Long> savePost(
            @RequestBody CreatePostRequestDto createPostRequestDto
    ) throws NotFoundException {
        Long postId = postService.save(createPostRequestDto.title(), createPostRequestDto.content(), createPostRequestDto.uuid());
        return ResponseEntity.ok(postId);
    }

    @PostMapping("/posts/{id}")
    public ResponseEntity<Long> updatePost(
            @PathVariable("id") Long id,
            @RequestBody UpdatePostRequestDto updatePostRequestDto
    ) throws NotFoundException {
        Long postId = postService.update(id, updatePostRequestDto.title(), updatePostRequestDto.content());
        return ResponseEntity.ok(postId);
    }
}
