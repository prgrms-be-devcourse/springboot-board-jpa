package com.prgrms.springbootboardjpa.controller;

import com.prgrms.springbootboardjpa.dto.PostDto;
import com.prgrms.springbootboardjpa.exception.NotFoundException;
import com.prgrms.springbootboardjpa.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/")
@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> notFoundHandler(NotFoundException e) {
        return new ResponseEntity<>(ApiResponse.of(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> internalServerErrorHandler(Exception e) {
        return new ResponseEntity<>(ApiResponse.of(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("posts/{id}")
    public ResponseEntity<ApiResponse<PostDto>> getOne(@PathVariable long id) throws NotFoundException {
        return ResponseEntity.ok(ApiResponse.of(postService.getOne(id)));
    }

    @GetMapping("posts")
    public ResponseEntity<ApiResponse<Page<PostDto>>> getAll(Pageable pageable) {
        return ResponseEntity.ok(ApiResponse.of(postService.getAll(pageable)));
    }

    @PostMapping("posts")
    public ResponseEntity<ApiResponse<PostDto>> save(@RequestBody PostDto postDto) {
        return ResponseEntity.ok(ApiResponse.of(postService.save(postDto)));
    }

    @PostMapping("posts/{id}")
    public ResponseEntity<ApiResponse<PostDto>> update(@PathVariable long id, @RequestBody PostDto postDto) throws NotFoundException {
        return ResponseEntity.ok(ApiResponse.of(postService.update(id, postDto)));
    }

    @DeleteMapping("posts/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable long id) throws NotFoundException {
        postService.delete(id);
        return ResponseEntity.ok(ApiResponse.of("성공적으로 삭제 됐습니다."));
    }
}
