package com.prgrms.springbootboardjpa.controller;


import com.prgrms.springbootboardjpa.dto.ApiResponse;
import com.prgrms.springbootboardjpa.dto.ContentDto;
import com.prgrms.springbootboardjpa.dto.PostDto;
import com.prgrms.springbootboardjpa.service.PostService;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PostController {

    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ApiResponse<String>> notFoundExceptionHandler(NotFoundException e) {
        return new ResponseEntity<>(ApiResponse.fail(404, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> exceptionHandler(Exception e) {
        return new ResponseEntity<>(ApiResponse.fail(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/posts")
    public ResponseEntity<ApiResponse<Long>> createPost(@RequestBody PostDto postDto) {
        Long save = postService.save(postDto);
        return new ResponseEntity<>(ApiResponse.created(save), HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public ResponseEntity<ApiResponse<Page<PostDto>>> getAllPost(Pageable pageable) {
        Page<PostDto> all = postService.findAllPosts(pageable);
        return new ResponseEntity<>(ApiResponse.ok(all), HttpStatus.OK);
    }

    @GetMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<PostDto>> getOnePost(@PathVariable Long postId) throws NotFoundException {
        PostDto one = postService.findById(postId);
        return new ResponseEntity<>(ApiResponse.ok(one), HttpStatus.OK);
    }

    @PatchMapping("/posts/{postId}")
    public ResponseEntity<ApiResponse<Long>> updatePostContent(@PathVariable Long postId,
        @RequestBody ContentDto contentDto) throws NotFoundException {
        Long update = postService.updateContent(postId, contentDto.content());
        return new ResponseEntity<>(ApiResponse.ok(update), HttpStatus.OK);
    }


}
