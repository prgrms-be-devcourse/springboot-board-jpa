package kdt.prgrms.devrun.post.controller;

import kdt.prgrms.devrun.common.dto.ApiResult;
import kdt.prgrms.devrun.common.dto.PageDto;
import kdt.prgrms.devrun.post.dto.AddPostRequestDto;
import kdt.prgrms.devrun.post.dto.DetailPostDto;
import kdt.prgrms.devrun.post.dto.EditPostRequestDto;
import kdt.prgrms.devrun.post.dto.SimplePostDto;
import kdt.prgrms.devrun.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@ResponseStatus(HttpStatus.OK)
@RequestMapping("/api/v1/posts")
public class PostApiController {

    private final PostService postService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<PageDto<SimplePostDto>> posts(Pageable pageable) {
        return ApiResult.ok(postService.getPostPagingList(pageable));
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<DetailPostDto> post(@PathVariable("id") Long postId) {
        return ApiResult.ok(postService.getPostById(postId));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<Long> create(@RequestBody @Valid AddPostRequestDto addPostRequestDto) {
        final Long createdPostId = postService.createPost(addPostRequestDto);
        return ApiResult.ok(createdPostId);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<Long> update(@PathVariable("id") Long postId ,@RequestBody @Valid EditPostRequestDto editPostRequestDto) {
        final Long updatedPostId = postService.updatePost(postId, editPostRequestDto);
        return ApiResult.ok(updatedPostId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ApiResult<Object> delete(@PathVariable("id") Long postId) {
        postService.deletePostById(postId);
        return ApiResult.ok(null);
    }

}
