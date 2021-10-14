package kdt.prgrms.devrun.post.controller;

import kdt.prgrms.devrun.common.dto.ApiResult;
import kdt.prgrms.devrun.post.dto.AddPostRequest;
import kdt.prgrms.devrun.post.dto.DetailPostDto;
import kdt.prgrms.devrun.post.dto.EditPostRequest;
import kdt.prgrms.devrun.post.dto.SimplePostDto;
import kdt.prgrms.devrun.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/api/v1/posts")
public class PostApiController {

    final private PostService postService;

    @GetMapping()
    public ApiResult<Page<SimplePostDto>> posts(Pageable pageable) {
        return ApiResult.ok(postService.getPostPagingList(pageable));
    }

    @GetMapping("/{id}")
    public ApiResult<DetailPostDto> post(@PathVariable("id") Long postId) {
        return ApiResult.ok(postService.getPostById(postId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public ApiResult<Long> create(@RequestBody @Valid AddPostRequest addPostRequest) {
        final Long createdPostId = postService.createPost(addPostRequest);
        return ApiResult.ok(createdPostId);
    }

    @PatchMapping("/{id}")
    public ApiResult<Long> update(@PathVariable("id") Long postId ,@RequestBody @Valid EditPostRequest editPostRequest) {
        final Long updatedPostId = postService.updatePost(postId, editPostRequest);
        return ApiResult.ok(updatedPostId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public ApiResult<Object> delete(@PathVariable("id") Long postId) {
        postService.deletePostById(postId);
        return ApiResult.ok(null);
    }

}
