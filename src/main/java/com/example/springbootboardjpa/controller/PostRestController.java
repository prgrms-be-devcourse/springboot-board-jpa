package com.example.springbootboardjpa.controller;

import com.example.springbootboardjpa.dto.PostDto;
import com.example.springbootboardjpa.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/posts")
public class PostRestController {
    @Autowired
    PostService postService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object create(@RequestBody @Valid PostDto postDto) {
        long result = postService.create(postDto);
        return result;
    }

    @PostMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object update1(@PathVariable long id, @RequestBody @Valid PostDto postDto) {
        String result = postService.update(id, postDto);
        return result;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Object get(Pageable pageable) {
        Page<PostDto> result = postService.readAll(pageable);
        return result;
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object getAll(@PathVariable Long id) {
        PostDto result = postService.read(id);
        return result;
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delete(@PathVariable Long id) {
        String result = postService.delete(id);
        return result;
    }
}
