package com.devco.jpaproject.controller;

import com.devco.jpaproject.controller.dto.PostDeleteRequestDto;
import com.devco.jpaproject.controller.dto.PostRequestDto;
import com.devco.jpaproject.controller.dto.PostResponseDto;
import com.devco.jpaproject.controller.dto.PostUpdateRequestDto;
import com.devco.jpaproject.exception.PostNotFoundException;
import com.devco.jpaproject.exception.UserAndPostNotMatchException;
import com.devco.jpaproject.exception.UserNotFoundException;
import com.devco.jpaproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class PostController {
    private static final String SUCCESS = "SUCCESS";

    private final PostService postService;

    @GetMapping("/posts")
    public ApiResponse<Page<PostResponseDto>> findAllByPages(Pageable pageable){
        var allPosts = postService.findAllByPages(pageable);
        return ApiResponse.ok(allPosts);
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostResponseDto> findById(@PathVariable Long id) throws PostNotFoundException {
        var postResponseDto = postService.findById(id);
        return ApiResponse.ok(postResponseDto);
    }

    @PostMapping("/posts")
    public ApiResponse<Long> insert(@Valid @RequestBody PostRequestDto dto) throws UserNotFoundException {
        Long postId = postService.insert(dto);

        return ApiResponse.created(postId);
    }

    @PatchMapping("/posts")
    public ApiResponse<String> update(@Valid @RequestBody PostUpdateRequestDto dto) throws PostNotFoundException, UserAndPostNotMatchException {
        postService.update(dto);

        return ApiResponse.ok(SUCCESS);
    }

    @DeleteMapping("/posts")
    public ApiResponse<String> deleteOne(@Valid @RequestBody PostDeleteRequestDto dto)
            throws PostNotFoundException, UserAndPostNotMatchException {
        postService.deleteOne(dto);

        return ApiResponse.ok(SUCCESS);
    }

}
