package com.board.springbootboard.domain.posts.controller;


import com.board.springbootboard.domain.posts.service.PostsService;
import com.board.springbootboard.domain.posts.dto.PostsResponseDto;
import com.board.springbootboard.domain.posts.dto.PostsSaveRequestDto;
import com.board.springbootboard.domain.posts.dto.PostsUpdateRequestsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    // 등록
    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    // id로 수정
    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestsDto requestsDto) {
        return postsService.update(id,requestsDto);
    }

    // id로 조회
    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }


    // 다건 조회
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("posts",postsService.findAllDesc());
        return "index";
    }




}
