package com.example.board.controller;

import com.example.board.ApiResponse;
import com.example.board.dto.PostDto;
import com.example.board.service.PostService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    PostController(PostService postService) {
        this.postService = postService;
    }

    @ExceptionHandler({NotFoundException.class})
    public ApiResponse<String> notFoundHandler (NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto> get(@PathVariable int id) throws NotFoundException {
        PostDto postDto =  postService.find(id);
        return ApiResponse.ok(postDto);
    }

    //게시글 조회
    @GetMapping()
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable){
        Page<PostDto> page = postService.findAll(pageable);
        return ApiResponse.ok(page);
    }

    @PostMapping
    public ApiResponse<Integer> save(@RequestBody PostDto postDto) {
        return ApiResponse.ok(postService.save(postDto));
    }
}
