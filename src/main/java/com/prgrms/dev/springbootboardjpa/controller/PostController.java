package com.prgrms.dev.springbootboardjpa.controller;

import com.prgrms.dev.springbootboardjpa.dto.PostDto;
import com.prgrms.dev.springbootboardjpa.dto.PostRequestDto;
import com.prgrms.dev.springbootboardjpa.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    //단건 조회 -> find
    @GetMapping("/{id}")
    public void findById(@PathVariable Long id) {
        PostDto postDto = postService.findById(id);
    }

    //페이지 조회 -> get
    @GetMapping
    public void getAll(Pageable pageable) {
        Page<PostDto> postDto = postService.getAll(pageable);
    }

    //생성
    @PostMapping
    public void create(@RequestBody PostRequestDto postRequestDto, @RequestParam Long userId) {
        PostDto postDto = postService.create(postRequestDto, userId);
    }

    //수정
    @PostMapping
    public void update(@RequestBody PostRequestDto requestDto, @RequestParam Long id) {
        PostDto update = postService.update(requestDto, id);
    }

}
