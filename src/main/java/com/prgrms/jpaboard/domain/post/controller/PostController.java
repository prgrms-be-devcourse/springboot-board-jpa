package com.prgrms.jpaboard.domain.post.controller;

import com.prgrms.jpaboard.domain.post.dto.request.PostUpdateDto;
import com.prgrms.jpaboard.domain.post.dto.response.PostDetailDto;
import com.prgrms.jpaboard.domain.post.dto.response.PostListDto;
import com.prgrms.jpaboard.domain.post.dto.request.PostCreateDto;
import com.prgrms.jpaboard.domain.post.service.PostService;
import com.prgrms.jpaboard.global.common.response.ResponseDto;
import com.prgrms.jpaboard.global.common.response.ResultDto;
import com.prgrms.jpaboard.global.common.resquest.PageParamDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping
    public ResponseEntity<ResponseDto> createPost(@Validated @RequestBody PostCreateDto postCreateDto){
        ResultDto result = postService.createPost(postCreateDto);

        ResponseDto responseDto = new ResponseDto(HttpStatus.CREATED.value(), "post created successfully", result);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getPosts(@Validated @ModelAttribute PageParamDto pageParamDto) {
        PostListDto postListDto = postService.getPosts(pageParamDto.getPage(), pageParamDto.getPerPage());

        ResponseDto responseDto = new ResponseDto(HttpStatus.OK.value(), "get posts successfully", postListDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getPost(@PathVariable Long id) {
        PostDetailDto postDetailDto = postService.getPost(id);

        ResponseDto responseDto = new ResponseDto(HttpStatus.OK.value(), "get post successfully", postDetailDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updatePost(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        ResultDto result = postService.updatePost(id, postUpdateDto);

        ResponseDto responseDto = new ResponseDto(HttpStatus.OK.value(), "post updated successfully", result);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseDto);
    }
}
