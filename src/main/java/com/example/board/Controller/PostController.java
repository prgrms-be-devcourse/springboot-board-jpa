package com.example.board.Controller;

import com.example.board.ApiResponse;
import com.example.board.Dto.PostDto;
import com.example.board.Dto.PostRequestDto;
import com.example.board.Service.PostService;
import com.example.board.domain.Post;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notfound(NotFoundException e){return ApiResponse.fail(404,e.getMessage());}

    @ExceptionHandler(Exception.class)
    public ApiResponse<String> notfound(Exception e){return ApiResponse.fail(500,e.getMessage());}


    @Autowired
    public PostService postService;

    @GetMapping("api/v1/posts")
    public ApiResponse<Page<PostDto>> findAll(Pageable pageable){
        Page<PostDto> all = postService.findAll(pageable);
        return ApiResponse.ok(all);

    }
    @GetMapping("api/v1/posts/{id}")
    public ApiResponse<PostDto> findOne(@PathVariable Long id) throws NotFoundException {
        PostDto one = postService.findOne(id);
        return ApiResponse.ok(one);


    }
    @PostMapping("api/v1/posts")
    public ApiResponse<Long> createPost(@RequestBody PostDto postdto){
        Long save = postService.save(postdto);
        return ApiResponse.ok(save);

    }
    @PatchMapping("api/v1/posts/{id}")
    public ApiResponse<PostDto> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto) throws NotFoundException {
        PostDto update = postService.update(id, postRequestDto.getTitle(),postRequestDto.getContent());
        return ApiResponse.ok(update);

    }




}
