package com.prgrms.jpa.controller;

import com.prgrms.jpa.controller.dto.post.CreatePostRequest;
import com.prgrms.jpa.controller.dto.post.CreatePostResponse;
import com.prgrms.jpa.controller.dto.post.FindAllPostResponse;
import com.prgrms.jpa.controller.dto.post.GetByIdPostResponse;
import com.prgrms.jpa.controller.dto.post.UpdatePostRequest;
import com.prgrms.jpa.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> create(@RequestBody @Valid CreatePostRequest createPostRequest) {
        CreatePostResponse id = postService.create(createPostRequest);
        return new ResponseEntity<>(id, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<FindAllPostResponse> findAll(@PageableDefault(size = 15) Pageable pageable) {
        FindAllPostResponse findAllPostResponse = postService.findAll(pageable);
        return ResponseEntity.ok(findAllPostResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetByIdPostResponse> getById(@PathVariable long id) {
        GetByIdPostResponse getByIdPostResponse = postService.getById(id);
        return ResponseEntity.ok(getByIdPostResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody @Valid UpdatePostRequest updatePostRequest) {
        postService.update(id, updatePostRequest);
        return ResponseEntity.ok().build();
    }
}
