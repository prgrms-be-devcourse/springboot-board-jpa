package com.programmers.domain.post.ui;

import com.programmers.common.dto.ApiResponse;
import com.programmers.domain.post.application.PostServiceImpl;
import com.programmers.domain.post.ui.dto.PostDto;
import com.programmers.domain.post.ui.dto.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostServiceImpl postServiceImpl;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiResponse<Long> createPost(@RequestBody PostDto postDto) {
        return new ApiResponse<>(postServiceImpl.createPost(postDto));
    }

    @GetMapping
    public List<PostDto> findPostList(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return postServiceImpl.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PostDto findPost(@PathVariable Long id) {
        return postServiceImpl.findPost(id);
    }

    @PatchMapping("/{id}")
    public PostDto updatePost(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        return postServiceImpl.updatePost(postUpdateDto, id);
    }
}
