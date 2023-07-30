package com.programmers.post.ui;

import com.programmers.post.application.PostService;
import com.programmers.post.ui.dto.PostDto;
import com.programmers.post.ui.dto.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public Long createPost(@RequestBody PostDto postDto) {
        return postService.createPost(postDto);
    }

    @GetMapping("/posts")
    public Page<PostDto> findPostList(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.findAll(pageable);
    }

    @GetMapping("/posts/{id}")
    public PostDto findPost(@PathVariable Long id) {
        return postService.findPost(id);
    }

    @PatchMapping("/posts/{id}")
    public PostDto updatePost(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        return postService.updatePost(postUpdateDto, id);
    }
}
