package com.programmers.domain.post.ui;

import com.programmers.common.dto.ApiResponse;
import com.programmers.domain.post.application.PostServiceImpl;
import com.programmers.domain.post.ui.dto.PostDto;
import com.programmers.domain.post.ui.dto.PostUpdateDto;
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

    private final PostServiceImpl postServiceImpl;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public ApiResponse createPost(@RequestBody PostDto postDto) {
        return new ApiResponse(postServiceImpl.createPost(postDto));
    }

    @GetMapping("/posts")
    public Page<PostDto> findPostList(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return postServiceImpl.findAll(pageable);
    }

    @GetMapping("/posts/{id}")
    public PostDto findPost(@PathVariable Long id) {
        return postServiceImpl.findPost(id);
    }

    @PatchMapping("/posts/{id}")
    public PostDto updatePost(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        return postServiceImpl.updatePost(postUpdateDto, id);
    }
}
