package com.kdt.prgrms.board.controller;

import com.kdt.prgrms.board.domain.Post;
import com.kdt.prgrms.board.dto.PostAddRequest;
import com.kdt.prgrms.board.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/posts")
public class PostApiController {

    private final PostService postService;

    public PostApiController(PostService postService) {

        this.postService = postService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void addPost(@RequestBody @Valid PostAddRequest postAddRequest) {

        postService.addPost(Post.from(postAddRequest));
    }
}
