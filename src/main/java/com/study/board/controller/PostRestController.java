package com.study.board.controller;

import com.study.board.controller.dto.PostResponse;
import com.study.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @GetMapping
    public List<PostResponse> findAll(Pageable pageable){
        return postService.findAll(pageable).stream().map(PostResponse::convert).collect(Collectors.toList());
    }

    @GetMapping("/{postId}")
    public PostResponse findById(@PathVariable Long postId){
        return PostResponse.convert(postService.findById(postId));
    }
}
