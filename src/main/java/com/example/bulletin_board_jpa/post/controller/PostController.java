package com.example.bulletin_board_jpa.post.controller;

import com.example.bulletin_board_jpa.post.dto.ApiResponse;
import com.example.bulletin_board_jpa.post.dto.PostRequestDto;
import com.example.bulletin_board_jpa.post.dto.PostResponseDto;
import com.example.bulletin_board_jpa.post.dto.PutRequestDto;
import com.example.bulletin_board_jpa.post.service.PostService;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
    public ApiResponse<String> notFoundHandler(ChangeSetPersister.NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerError(Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }


    @PostMapping("/posts")
    public ApiResponse<Long> postBoard(@RequestBody PostRequestDto postRequestDto) {
        Long save = postService.save(postRequestDto);
        return new ApiResponse<>(201, save);
    }

    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getOne(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        PostResponseDto one = postService.findOne(id);
        return new ResponseEntity<>(one, HttpStatus.OK);
    }

    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDto>> getAll(Pageable pageable) {
        List<PostResponseDto> all = postService.findAll(pageable);
        return new ResponseEntity<>(all, HttpStatus.OK);
    }

    @PutMapping("/posts/{id}")
    public ApiResponse<Long> update(@PathVariable Long id, @RequestBody PutRequestDto putRequestDto) throws ChangeSetPersister.NotFoundException {
        Long update = postService.update(id, putRequestDto);
        System.out.println(update);
        return new ApiResponse<>(200, update);
    }
}
