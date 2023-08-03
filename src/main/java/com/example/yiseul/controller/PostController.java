package com.example.yiseul.controller;

import com.example.yiseul.dto.post.PostCreateRequestDto;
import com.example.yiseul.dto.post.PostPageResponseDto;
import com.example.yiseul.dto.post.PostResponseDto;
import com.example.yiseul.dto.post.PostUpdateRequestDto;
import com.example.yiseul.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public PostResponseDto createPost(@RequestBody @Valid PostCreateRequestDto createRequestDto) {

        return postService.createPost(createRequestDto);
    }

    @GetMapping
    public PostPageResponseDto getPosts(Pageable pageable){

        return postService.getPosts(pageable);
    }

    @GetMapping("/{postId}") // -> posts/1
    public PostResponseDto getPost(@PathVariable Long postId){

        return postService.getPost(postId);
    }

    @PatchMapping("/{postId}")
    public void updatePost(
            @PathVariable Long postId,
            @RequestBody @Valid PostUpdateRequestDto updateRequestDto) {

        postService.updatePost(postId, updateRequestDto);

    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){

        postService.deletePost(postId);
    }
}
