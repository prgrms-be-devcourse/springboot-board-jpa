package com.prgrms.jpaboard.domain.post.controller;

import com.prgrms.jpaboard.domain.post.dto.PostListDto;
import com.prgrms.jpaboard.domain.post.dto.PostRequestDto;
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
    public ResponseEntity<ResponseDto> createPost(@Validated @RequestBody PostRequestDto postRequestDto){
        ResultDto result = postService.createPost(postRequestDto);

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
}
