package com.kdt.board.domain.controller;

import com.kdt.board.domain.controller.api.ApiResponse;
import com.kdt.board.domain.dto.PostDto;
import com.kdt.board.domain.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ApiResponse<Page<PostDto.Response>> getAll(
            Pageable pageable
    ) {
        Page<PostDto.Response> posts = postService.findAll(pageable);
        return ApiResponse.ok(posts);
    }

    @PostMapping
    public ApiResponse<PostDto.CheckingId> save(
            @RequestBody PostDto.Save dto
    ) {
        Long id = postService.save(dto);
        PostDto.CheckingId responseData = new PostDto.CheckingId(id);
        return ApiResponse.ok(responseData);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostDto.Response> getOne(
            @PathVariable Long id
    ) {
        PostDto.Response findOne = postService.findById(id);
        return ApiResponse.ok(findOne);
    }


    @PutMapping
    public ApiResponse<PostDto.Response> update(
            @RequestBody PostDto.Update dto
    ) {
        PostDto.Response updatedDto = postService.update(dto);
        return ApiResponse.ok(updatedDto);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<PostDto.CheckingId> deleteById(
            @PathVariable Long id
    ) {
        postService.deleteById(id);
        PostDto.CheckingId responseData = new PostDto.CheckingId(id);
        return ApiResponse.ok(responseData);
    }
}
