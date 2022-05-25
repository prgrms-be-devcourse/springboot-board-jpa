package com.prgrms.boardjpa.post.controller;

import com.prgrms.boardjpa.Response;
import com.prgrms.boardjpa.post.dto.PostReqDto;
import com.prgrms.boardjpa.post.dto.PostResDto;
import com.prgrms.boardjpa.post.dto.PostUpdateDto;
import com.prgrms.boardjpa.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor // 생성자 주입
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    @ExceptionHandler
    private Response<String> handleException(Exception exception) {
        return Response.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @GetMapping("/posts/{postId}")
    public Response<PostResDto> getOne(@PathVariable Long postId){
        return Response.ok(postService.findOne(postId), "게시글 단건 조회 성공");
    }

    @GetMapping("/posts")
    public Response<Page<PostResDto>> getAll (Pageable pageable){
        return Response.ok(postService.findPosts(pageable), "게시글 다건 조회 성공");
    }

    @PostMapping("/posts")
    public Response<PostResDto> save (@RequestBody PostReqDto postReqDto){
        return Response.ok(postService.save(postReqDto), "게시글 생성 성공");
    }

    @PutMapping("/posts/{postId}")
    public Response<PostResDto> update(@PathVariable Long postId, @RequestBody PostUpdateDto postUpdateDto){
        return Response.ok(postService.update(postId, postUpdateDto), "게시글 수정 성공");
    }
}
