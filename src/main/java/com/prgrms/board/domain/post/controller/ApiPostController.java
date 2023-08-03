package com.prgrms.board.domain.post.controller;

import static com.prgrms.board.global.common.SuccessMessage.*;
import static org.springframework.http.HttpStatus.*;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.board.domain.post.dto.request.PostCreateRequest;
import com.prgrms.board.domain.post.dto.request.PostUpdateRequest;
import com.prgrms.board.domain.post.dto.response.PostDetailResponse;
import com.prgrms.board.domain.post.dto.response.PostResponse;
import com.prgrms.board.domain.post.entity.Post;
import com.prgrms.board.domain.post.service.PostService;
import com.prgrms.board.global.common.dto.BaseResponse;
import com.prgrms.board.global.common.dto.PageResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class ApiPostController {

    private final PostService postService;

    @GetMapping
    public BaseResponse<PageResponse<Post>> getPosts(
        @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PageResponse<Post> data = postService.getPosts(pageable);
        return BaseResponse.ok(GET_POSTS_SUCCESS, data);
    }

    @GetMapping("/{postId}")
    public BaseResponse<PostDetailResponse> getPost(@PathVariable Long postId) {
        PostDetailResponse data = postService.getPost(postId);
        return BaseResponse.ok(GET_POST_SUCCESS, data);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public BaseResponse<PostResponse> createPost(@Valid @RequestBody PostCreateRequest request) {
        PostResponse data = postService.createPost(request);
        return BaseResponse.created(CREATE_POST_SUCCESS, data);
    }

    @PatchMapping("/{postId}")
    public BaseResponse<PostResponse> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest request) {
        PostResponse data = postService.updatePost(postId, request);
        return BaseResponse.ok(UPDATE_POST_SUCCESS, data);
    }
}
