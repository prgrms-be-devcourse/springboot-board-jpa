package com.example.jpaboard.post.controller;

import com.example.jpaboard.global.ApiResponse;
import com.example.jpaboard.global.SliceResponse;
import com.example.jpaboard.global.SuccessCode;
import com.example.jpaboard.post.controller.dto.FindAllApiRequest;
import com.example.jpaboard.post.controller.dto.SaveApiRequest;
import com.example.jpaboard.post.controller.dto.UpdateApiRequest;
import com.example.jpaboard.post.controller.mapper.PostApiMapper;
import com.example.jpaboard.post.service.PostService;
import com.example.jpaboard.post.service.dto.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController("/posts")
public class PostController {

    private final PostService postService;
    private final PostApiMapper postApiMapper;

    public PostController(PostService postService, PostApiMapper postApiMapper) {
        this.postService = postService;
        this.postApiMapper = postApiMapper;
    }

    @GetMapping
    SliceResponse<PostResponse> findAllBy(@ModelAttribute FindAllApiRequest findAllApiRequest, Pageable pageable) {
        FindAllRequest findAllRequest = postApiMapper.toFindAllRequest(findAllApiRequest);

        return new SliceResponse<>(postService.findPostAllByFilter(findAllRequest, pageable), SuccessCode.SELECT_SUCCESS);
    }

    @PatchMapping("/{id}")
    ApiResponse<PostResponse> updatePost (@PathVariable Long postId, @RequestBody UpdateApiRequest updateApiRequest) {
        UpdateRequest updateRequest = postApiMapper.toUpdateRequest(updateApiRequest);
        return new ApiResponse<>(postService.updatePost(postId, updateRequest),SuccessCode.UPDATE_SUCCESS);
    }

    @GetMapping("/{id}")
    ApiResponse<PostResponse> findById (@PathVariable Long id) {
        return new ApiResponse<>(postService.findById(id), SuccessCode.SELECT_SUCCESS);
    }

    @PostMapping
    ResponseEntity<PostResponse> savePost (@RequestBody SaveApiRequest saveApiRequest ) {
        SaveRequest saveRequest = postApiMapper.toSaveRequest(saveApiRequest);
        PostResponse saveResponse =postService.savePost(saveRequest);

        URI location =  ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveResponse.postId())
                .toUri();

        return ResponseEntity.created(location).body(saveResponse);
    }

}
