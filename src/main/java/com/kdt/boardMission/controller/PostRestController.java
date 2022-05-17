package com.kdt.boardMission.controller;

import com.kdt.boardMission.ApiResponse;
import com.kdt.boardMission.dto.CreatePostDto;
import com.kdt.boardMission.dto.PostDto;
import com.kdt.boardMission.dto.UserDto;
import com.kdt.boardMission.service.PostService;
import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostRestController {

    private final PostService postService;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> interalServerErrorHandler(Exception e) {
        return ApiResponse.fail(500, e.getMessage());
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> findAll(Pageable pageable) {
        Page<PostDto> all = postService.findAll(pageable);
        return ApiResponse.ok(all);
    }

    @GetMapping("/posts/{id}")
    public ApiResponse<PostDto> findOne(@PathVariable("id") long id) throws NotFoundException {
        PostDto byId = postService.findById(id);
        return ApiResponse.ok(byId);
    }

    @PostMapping("/posts")
    public ApiResponse<Long> doPost(@RequestBody CreatePostDto createPostDto) throws NotFoundException {
        PostDto postDto = createPostDto.getPostDto();
        UserDto userDto = createPostDto.getUserDto();

        long postId = postService.savePost(postDto, userDto);
        return ApiResponse.ok(postId);
    }

    @PostMapping("/posts/{id}")
    public ApiResponse<PostDto> editPost(@PathVariable("id") Long id,
                                         @RequestBody PostDto postDto) throws NotFoundException {
        postDto.setId(id);
        postService.updatePost(postDto);
        return ApiResponse.ok(postDto);
    }
}
