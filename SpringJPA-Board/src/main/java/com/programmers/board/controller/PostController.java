package com.programmers.board.controller;

import com.programmers.board.ApiResponse;
import com.programmers.board.dto.PostDto;
import com.programmers.board.service.PostService;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
public class PostController {

    /*생성자 주입 방식을 쓴 이유: @Autowired에 비해 생성자 주입 방식은 final 인자로 받기 때문에 순환 참조를 막을 수 있다*/
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts") // 게시글 모두 조회
    public ApiResponse<Page<PostDto>> getAll(Pageable pageable) {
        Page<PostDto> all = postService.findAll(pageable);
        return ApiResponse.ok(all);
    }

    @GetMapping("/posts/{id}") // 게시글 단건 조회
    public ApiResponse<PostDto> getOne(@PathVariable Long id) throws NotFoundException {
        PostDto postDto = postService.findOneById(id);
        return ApiResponse.ok(postDto);
    }

    @PostMapping("/posts") // 게시글 생성
    public ApiResponse<Long> save(@RequestBody PostDto postDto) {
        Long id = postService.save(postDto);
        return ApiResponse.ok(id);
    }

    @PostMapping("/posts/{id}") // 게시글 수정
    public ApiResponse<PostDto> update(@RequestBody PostDto postDto) throws NotFoundException {
        PostDto updatedPost = postService.update(postDto);
        return ApiResponse.ok(updatedPost);
    }

    @DeleteMapping("/posts/{id}") // 게시글 삭제
    public ApiResponse<Long> delete(@PathVariable Long id) throws NotFoundException{
        postService.deleteOneById(id);
        return ApiResponse.ok(id);
    }
}
