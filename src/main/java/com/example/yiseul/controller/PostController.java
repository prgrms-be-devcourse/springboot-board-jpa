package com.example.yiseul.controller;

import com.example.yiseul.dto.post.PostCreateRequestDto;
import com.example.yiseul.dto.post.PostResponseDto;
import com.example.yiseul.dto.post.PostUpdateRequestDto;
import com.example.yiseul.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping // 질문 : memberID를 requestparam로 사용해야 할 지 createrequestdto에 넣어서 사용하는 것이 좋을지
    public PostResponseDto createPost(@RequestBody PostCreateRequestDto createRequestDto) {

        return postService.createPost(createRequestDto);
    }

    @GetMapping
    public Page<PostResponseDto> getAllPosts(Pageable pageable){

        return postService.getPosts(pageable);
    }

    @GetMapping("/{postId}") // -> posts/1
    public PostResponseDto getPost(@PathVariable Long postId){

        return postService.getPost(postId);
    }

    @PatchMapping("/{postId}") // 질문 : memberId로 작성자 확인을 해줘야할 지?
    public void updatePost(
            @PathVariable Long postId,
            @RequestBody PostUpdateRequestDto updateRequestDto) {

        postService.updatePost(postId, updateRequestDto);

    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){

        postService.deletePost(postId);
    }
}
