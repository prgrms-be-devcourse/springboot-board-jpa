package org.programmers.dev.domain.post.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.programmers.dev.domain.post.application.PostService;
import org.programmers.dev.domain.post.controller.dto.CreatePostDto;
import org.programmers.dev.domain.post.controller.dto.PostResponse;
import org.programmers.dev.domain.post.controller.dto.UpdatePostDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("posts")
public class PostController {

    private final PostService postService;

    @GetMapping("")
    public List<PostResponse> getAll() {
        return postService.getAll();
    }

    @GetMapping("/{id}")
    public PostResponse findById(@PathVariable Long id) {
        return postService.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Long create(@RequestBody CreatePostDto createPostDto) {
        return postService.create(createPostDto);
    }

    @PatchMapping("/{id}")
    public Long update(@PathVariable Long id, @RequestBody UpdatePostDto updatePostDto) {
        return postService.update(id, updatePostDto);
    }

}
