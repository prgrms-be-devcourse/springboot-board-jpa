package kdt.prgrms.devrun.post.controller;

import kdt.prgrms.devrun.common.dto.ApiResult;
import kdt.prgrms.devrun.post.dto.DetailPostDto;
import kdt.prgrms.devrun.post.dto.PostForm;
import kdt.prgrms.devrun.post.dto.SimplePostDto;
import kdt.prgrms.devrun.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class PostApiController {

    final private PostService postService;

    @GetMapping("/api/v1/posts")
    public ApiResult<Page<SimplePostDto>> posts(Pageable pageable) {
        return ApiResult.ok(postService.getPostPagingList(pageable));
    }

    @GetMapping("/api/v1/post/{id}")
    public ApiResult<DetailPostDto> post(@PathVariable("id") Long postId) {
        return ApiResult.ok(postService.getPostById(postId));
    }

    @PostMapping("/api/v1/post")
    public ApiResult<Long> create(@RequestBody @Valid PostForm postForm) {
        final Long createdPostId = postService.createPost(postForm);
        return ApiResult.ok(createdPostId);
    }

    @PatchMapping("/api/v1/post/{id}")
    public ApiResult<Long> update(@PathVariable("id") Long postId ,@RequestBody @Valid PostForm postForm) {
        final Long updatedPostId = postService.updatePost(postId, postForm);
        return ApiResult.ok(updatedPostId);
    }

    @DeleteMapping("/api/v1/post/{id}")
    public ApiResult<Object> delete(@PathVariable("id") Long postId) {
        postService.deletePostById(postId);
        return ApiResult.ok(null);
    }

}
