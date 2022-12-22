package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.dto.PostDTO;
import com.example.springbootboardjpa.exception.NotFoundException;
import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> internalServerErrorHandler(NotFoundException e) {
        return ApiResponse.fail(500, e.getMessage());
    }


    @GetMapping("/posts")
    public ApiResponse<Post> getPost(){
        return null;
    }

    /**
     *  게시물 단건 조회
     * @param id
     * @return PostDto
     * @throws NotFoundException
     */
    @GetMapping("/posts/{id}")
    public ApiResponse<PostDTO.Response> getPosts(@PathVariable Long id) throws NotFoundException {
        PostDTO.Response findPost = postService.findById(id);
        return ApiResponse.ok(findPost);
    }

    /**
     *  게시물 생성
     * @param postDTO
     * @return postId
     */
    @PostMapping("/posts")
    public ApiResponse<String> createPost(@Valid @RequestBody PostDTO.Save postDTO){
        log.info(postDTO.getUserDto().getHobby());
        long postId = postService.save(postDTO);
        return ApiResponse.ok(postId+"");
    }

    /**
     *  게시물 수정
     * @param id
     * @param postDTO
     * @throws NotFoundException
     */
    @PostMapping("/posts/{id}")
    public ApiResponse<String> updatePost(@PathVariable Long id,@Valid @RequestBody PostDTO.Request postDTO) throws NotFoundException {
        postService.update(id, postDTO.getTitle(), postDTO.getContent());
        return ApiResponse.ok("업데이트 성공");
    }
}
