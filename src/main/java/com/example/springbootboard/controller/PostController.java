package com.example.springbootboard.controller;

import com.example.springbootboard.dto.PostCreateRequest;
import com.example.springbootboard.facade.PostFacade;
import com.example.springbootboard.service.PostService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    private final PostFacade postFacade;

    public PostController(PostService postService, PostFacade postFacade) {
        this.postService = postService;
        this.postFacade = postFacade;
    }

//    @PostMapping("/posts")
//    public ResponseEntity<> createPost(@RequestBody @Valid PostCreateRequest request){
//
//    }
}
