package com.example.jpaboard.post.controller;

import com.example.jpaboard.global.ApiResponse;
import com.example.jpaboard.global.SliceResponse;
import com.example.jpaboard.global.SuccessCode;
import com.example.jpaboard.post.controller.dto.PostFindApiRequest;
import com.example.jpaboard.post.controller.dto.PostSaveApiRequest;
import com.example.jpaboard.post.controller.dto.PostUpdateApiRequest;
import com.example.jpaboard.post.controller.mapper.PostApiMapper;
import com.example.jpaboard.post.service.PostService;
import com.example.jpaboard.post.service.dto.*;

import jakarta.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final PostApiMapper postApiMapper;

    public PostController(PostService postService, PostApiMapper postApiMapper) {
        this.postService = postService;
        this.postApiMapper = postApiMapper;
    }

    @GetMapping
    public SliceResponse<PostResponse> findAllBy(@ModelAttribute PostFindApiRequest postRetrieveApiRequest, Pageable pageable) {
        PostFindRequest postFindRequest = postApiMapper.toFindAllRequest(postRetrieveApiRequest);

        Slice<PostResponse> postAllByFilter = postService.findAllByFilter(postFindRequest, pageable);
        SliceResponse<PostResponse> postResponseSliceResponse = new SliceResponse<>(postAllByFilter, SuccessCode.SELECT_SUCCESS);
        return postResponseSliceResponse;
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse<PostResponse> updatePost(@PathVariable Long id, @RequestBody @Valid PostUpdateApiRequest postUpdateApiRequest) {
        PostUpdateRequest postUpdateRequest = postApiMapper.toUpdateRequest(postUpdateApiRequest);
        return new ApiResponse<>(postService.updatePost(id, postUpdateRequest), SuccessCode.UPDATE_SUCCESS);
    }

    @GetMapping("/{id}")
    public ApiResponse<PostResponse> findById(@PathVariable Long id) {
        return new ApiResponse<>(postService.findById(id), SuccessCode.SELECT_SUCCESS);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostResponse> savePost(@RequestBody @Valid PostSaveApiRequest postSaveApiRequest) {
        PostSaveRequest postSaveRequest = postApiMapper.toSaveRequest(postSaveApiRequest);
        PostResponse saveResponse = postService.savePost(postSaveRequest);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveResponse.postId())
                .toUri();

        return ResponseEntity.created(location).body(saveResponse);
    }

}
