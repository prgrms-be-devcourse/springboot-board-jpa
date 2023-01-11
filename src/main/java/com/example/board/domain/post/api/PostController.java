package com.example.board.domain.post.api;

import static com.example.board.domain.post.dto.PostDto.*;
import static org.springframework.data.domain.Sort.Direction.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.board.domain.post.service.PostService;
import com.example.board.global.dto.BaseResponse;
import com.example.board.global.dto.PageCustomResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @GetMapping
    public BaseResponse<PageCustomResponse<SinglePostResponse>> pagingPosts(
            @PageableDefault(sort = "createdAt", direction = DESC) Pageable pageable
    ) {
        Page<SinglePostResponse> pagedResponse = postService.pagingPost(pageable);
        return BaseResponse.of(HttpStatus.OK,
                "response.paging-posts.title",
                "response.paging-posts.content",
                PageCustomResponse.of(pagedResponse.getContent(), pagedResponse.getPageable(), pagedResponse.getTotalElements()));
    }

    @GetMapping("/{id}")
    public BaseResponse<SinglePostResponse> getSinglePost(@PathVariable(name = "id") long postId) {
        return BaseResponse.of(HttpStatus.OK,
                "response.get-single-post.title",
                "response.get-single-post.content",
                postService.getPost(postId));
    }

    @PostMapping
    public BaseResponse<SinglePostResponse> enrollPost(@RequestBody @Valid CreatePostRequest createPostRequest) {
        return BaseResponse.of(HttpStatus.OK,
                "response.enroll-post.title",
                "response.enroll-post.content",
                postService.post(createPostRequest));
    }

    @PatchMapping("/{id}")
    public BaseResponse<SinglePostResponse> updatePost(@PathVariable(name = "id") long postId,
                                                       @RequestBody @Valid UpdatePostRequest updatePostRequest
    ) {
        return BaseResponse.of(HttpStatus.OK,
                "response.update-post.title",
                "response.update-post.content",
                postService.updatePost(postId, updatePostRequest));
    }
}
