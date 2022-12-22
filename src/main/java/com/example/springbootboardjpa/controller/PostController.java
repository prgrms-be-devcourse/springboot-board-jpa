package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.exception.NotFoundException;
import com.example.springbootboardjpa.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(NotFoundException e) {
        return ApiResponse.fail(500, e.getMessage());
    }


    /**
     * 게시물 page 조회
     *
     * @param pageable
     * @return Page<PostDto>
     */
    @GetMapping("/posts")
    public ApiResponse<Page<PostDTO.Response>> getPost(Pageable pageable) {
        Page<PostDTO.Response> pages = postService.findAll(pageable);
        return ApiResponse.ok(pages);
    }

    /**
     * 게시물 단건 조회
     *
     * @param id
     * @return PostDto
     */
    @GetMapping("/posts/{id}")
    public ApiResponse<PostDTO.Response> getPosts(@PathVariable Long id) {
        PostDTO.Response findPost = postService.findById(id);
        return ApiResponse.ok(findPost);
    }

    /**
     * 게시물 생성
     *
     * @param postDTO
     * @return PostId
     */
    @PostMapping("/posts")
    public ApiResponse<String> createPost(@Valid @RequestBody PostDTO.Save postDTO) {
        log.info(postDTO.getUserDto().getHobby());
        long postId = postService.save(postDTO);
        return ApiResponse.ok(postId + "");
    }

    /**
     * 게시물 수정
     *
     * @param id
     * @param postDTO
     */
    @PostMapping("/posts/{id}")
    public void updatePost(@PathVariable Long id, @Valid @RequestBody PostDTO.Request postDTO) {
        postService.update(id, postDTO.getTitle(), postDTO.getContent());
    }
}
