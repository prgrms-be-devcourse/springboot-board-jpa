package com.example.jpaboard.web.controller;

import com.example.jpaboard.service.post.dto.PostSaveRequest;
import com.example.jpaboard.service.post.dto.PostResponse;
import com.example.jpaboard.service.post.PostService;
import com.example.jpaboard.service.post.dto.PostUpdateRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("api/v1/posts")
@RestController
public class PostApiController {
    private final PostService postService;

    public PostApiController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> findAll(@PageableDefault(size = 10, sort = {"createdAt"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PostResponse findById(@PathVariable(value = "id") Long id) {
        return postService.findById(id);
    }

    @PostMapping
    public void save(@RequestBody @Valid PostSaveRequest request) {
        postService.save(request.getUserId(), request.getTitle(), request.getContent());
    }

    @PatchMapping("/{id}")
    public PostResponse update(@PathVariable Long id, @ModelAttribute @Valid PostUpdateRequest request) {
        return postService.update(id, request.getTitle(), request.getContent());
    }
}
