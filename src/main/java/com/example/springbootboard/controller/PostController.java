package com.example.springbootboard.controller;

import com.example.springbootboard.facade.PostFacade;
import com.example.springbootboard.service.PostService;
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


}
