package com.programmers.domain.post.ui;

import com.programmers.common.dto.ApiIdResponse;
import com.programmers.domain.post.application.PostService;
import com.programmers.domain.post.ui.dto.PostCreateDto;
import com.programmers.domain.post.ui.dto.PostResponseDto;
import com.programmers.domain.post.ui.dto.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ApiIdResponse createPost(@RequestBody PostCreateDto postCreateDto) {
        return new ApiIdResponse(postService.createPost(postCreateDto));
    }

    @GetMapping
    public List<PostResponseDto> findPostList(@PageableDefault(size = 20, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return postService.findAll(pageable);
    }

    @GetMapping("/{id}")
    public PostResponseDto findPost(@PathVariable Long id) {
        return postService.findPost(id);
    }

    @PatchMapping("/{id}")
    public PostResponseDto updatePost(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        return postService.updatePost(postUpdateDto, id);
    }
}
