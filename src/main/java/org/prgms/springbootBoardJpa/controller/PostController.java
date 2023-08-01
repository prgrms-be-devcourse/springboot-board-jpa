package org.prgms.springbootBoardJpa.controller;

import org.prgms.springbootBoardJpa.service.PostCreateRequest;
import org.prgms.springbootBoardJpa.service.PostResponse;
import org.prgms.springbootBoardJpa.service.PostService;
import org.prgms.springbootBoardJpa.service.PostUpdateRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody PostCreateRequest request) {
        postService.create(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Long id, @RequestBody PostUpdateRequest request) {
        postService.update(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> findAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(postService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findOne(@PathVariable("id") Long id) {
        return new ResponseEntity<>(postService.findOne(id), HttpStatus.OK);
    }
}
