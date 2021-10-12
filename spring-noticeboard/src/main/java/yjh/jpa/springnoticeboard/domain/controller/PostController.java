package yjh.jpa.springnoticeboard.domain.controller;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import yjh.jpa.springnoticeboard.domain.ApiResponse;
import yjh.jpa.springnoticeboard.domain.dto.PostDto;
import yjh.jpa.springnoticeboard.domain.service.PostService;

@Slf4j
@RestController
@RequestMapping(path = "/board/posts")
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping(path = "{postId}")
    public ApiResponse<PostDto> getPost(@PathVariable(name = "postId") Long postId) throws NotFoundException {
        PostDto postDto = postService.findPost(postId);
        return ApiResponse.ok(postDto);
    }

    @GetMapping(path = "")
    public ApiResponse<Page<Object>> getPostList(Pageable pageable){
        Page<Object> objects = postService.findAll(pageable);
        return ApiResponse.ok(objects);
    }

    @PostMapping(path = "")
    public ApiResponse<Long> insertPost(@RequestBody PostDto postDto) throws NotFoundException {
        Long insert = postService.createPost(postDto);
        return ApiResponse.ok(insert);
    }

    @PatchMapping(path = "{postId}")
    public ApiResponse<Long> updatePost(@PathVariable(name = "postId") Long postId, @RequestBody PostDto postDto) throws NotFoundException {
        Long update = postService.updatePost(postId, postDto);
        return ApiResponse.ok(update);
    }

    @DeleteMapping(path = "{postId}")
    public void removePost(@PathVariable(name = "postId") Long postId) throws NotFoundException {
        postService.deletePost(postId);
    }

    @DeleteMapping(path = "")
    public void removePostList(){
        postService.deleteAll();
    }

}
