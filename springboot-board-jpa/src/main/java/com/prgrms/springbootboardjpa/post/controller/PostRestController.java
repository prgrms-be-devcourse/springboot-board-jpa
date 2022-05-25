package com.prgrms.springbootboardjpa.post.controller;

import com.prgrms.springbootboardjpa.post.dto.PostDto;
import com.prgrms.springbootboardjpa.post.dto.PostResponse;
import com.prgrms.springbootboardjpa.post.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PostRestController {
    private final String PATH = "/api/v1/posts";

    @Autowired
    PostService postService;

    @PostMapping(path = PATH)
    public PostResponse save(@RequestBody @Valid PostDto postDto){
        return postService.save(postDto);
    }

    @GetMapping(path = PATH)
    public List<PostResponse> allPosts(Pageable pageable){
        return postService.findAll(pageable).stream().toList();
    }

    @PutMapping(path = PATH + "/{postId}")
    public PostResponse update(@PathVariable("postId") Long id, @RequestBody @Valid PostDto postDto){
        postDto.setId(id);
        return postService.update(postDto);
    }
}
