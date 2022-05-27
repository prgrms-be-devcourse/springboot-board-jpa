package com.study.board.controller;

import com.study.board.controller.dto.PostRequest;
import com.study.board.controller.dto.PostResponse;
import com.study.board.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @GetMapping
    public List<PostResponse> findAll(Pageable pageable) {
        return postService.findAll(pageable).stream().map(PostResponse::convert).collect(Collectors.toList());
    }

    @GetMapping("/{postId}")
    public PostResponse findById(@PathVariable Long postId) {
        return PostResponse.convert(postService.findById(postId));
    }

    /**
     * 인증 필요 - http 인가 헤더에 사용자 이름을 포함하는 것으로 대체
     */
    @PostMapping
    public PostResponse upload(@RequestHeader("Authorization") String username, @RequestBody PostRequest postRequest) {
        return PostResponse.convert(postService.write(
                postRequest.getTitle(),
                postRequest.getContent(),
                username
        ));
    }

    /**
     * 인증 필요 - http 인가 헤더에 사용자 이름을 포함하는 것으로 대체
     */
    @PutMapping("/{postId}")
    public PostResponse edit(@RequestHeader("Authorization") String username, @RequestBody PostRequest postRequest, @PathVariable Long postId) {
        return PostResponse.convert(postService.edit(
                postId,
                postRequest.getTitle(),
                postRequest.getContent(),
                username
        ));
    }
}
