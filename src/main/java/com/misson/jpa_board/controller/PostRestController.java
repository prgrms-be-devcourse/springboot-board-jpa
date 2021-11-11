package com.misson.jpa_board.controller;

import com.misson.jpa_board.ApiResponse;
import com.misson.jpa_board.dto.PostCreateRequest;
import com.misson.jpa_board.dto.PostDto;
import com.misson.jpa_board.service.PostService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/posts")
@RestController
public class PostRestController {

    private final PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @PostMapping
    public ApiResponse<Long> save(@RequestBody PostCreateRequest postDto) throws NotFoundException {
        log.info("게시글 작성");
        return ApiResponse.ok(postService.save(postDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<PostDto> changePost(@PathVariable Long id, @RequestBody PostDto postDto) {
        log.info("게시글 수정");
        return ApiResponse.ok(postService.postChange(id, postDto));
    }

    @GetMapping
    public ApiResponse<Page<PostDto>> getPage(Pageable pageable) {
        log.info("게시글 페이지 조회");
        return ApiResponse.ok(postService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto> postFindById(@PathVariable(value = "id") Long id) throws NotFoundException {
        log.info("게시글 단건 조회");
        PostDto postDto = postService.postFindById(id);
        return ApiResponse.ok(postDto);
    }

}
