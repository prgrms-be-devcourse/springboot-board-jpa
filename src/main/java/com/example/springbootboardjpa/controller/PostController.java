package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.model.Post;
import com.example.springbootboardjpa.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public ApiResponse<Post> getPost(){
        return null;
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<List<Post>> getPosts(@PathVariable String id){
        return null;
    }

    @PostMapping("/posts")
    public ApiResponse<String> createPost(){

        return null;
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<Void> updatePost(@PathVariable String id){
        return null;
    }
}
