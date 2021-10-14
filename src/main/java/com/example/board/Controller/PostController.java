package com.example.board.Controller;

import com.example.board.ApiResponse;
import com.example.board.Dto.PostDto;
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
    public ApiResponse<Post> createPost(@RequestBody PostDto postdto){
        Post save = postService.save(postdto);
        return ApiResponse.ok(save);

    }
    @PostMapping("api/v1/posts/{id}")
    public ApiResponse<Post> updatePost(@PathVariable Long id, @RequestBody PostDto postDto) throws NotFoundException {
        Post update = postService.update(id, postDto);
        return ApiResponse.ok(update);

    }




}
