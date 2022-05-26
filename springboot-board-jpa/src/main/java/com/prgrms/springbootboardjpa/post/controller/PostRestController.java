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
@RequestMapping(value = "/api/v1/posts")
public class PostRestController {

    private final PostService postService;


    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public PostResponse save(@RequestBody @Valid PostDto postDto){
        return postService.save(postDto);
    }

    @GetMapping
    public List<PostResponse> allPosts(Pageable pageable){
        return postService.findAll(pageable).stream().toList();
    }

    @PutMapping( "/{postId}")
    public PostResponse update(@PathVariable("postId") Long id, @RequestBody @Valid PostDto postDto){
        postDto.setId(id);
        return postService.update(postDto);
    }
}
