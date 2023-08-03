package com.programmers.board.controller;

import com.programmers.board.constant.SessionConst;
import com.programmers.board.controller.response.PageResult;
import com.programmers.board.controller.response.Result;
import com.programmers.board.dto.PostDto;
import com.programmers.board.dto.request.PostCreateRequest;
import com.programmers.board.dto.request.PostUpdateRequest;
import com.programmers.board.dto.request.PostsGetRequest;
import com.programmers.board.exception.AuthenticationException;
import com.programmers.board.service.PostService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/v1")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts")
    public PageResult<PostDto> findPosts(@ModelAttribute @Valid PostsGetRequest request) {
        Page<PostDto> posts = postService.findPosts(
                request.getPage(),
                request.getSize());
        return new PageResult<>(posts);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/posts")
    public Result<Long> createPost(@SessionAttribute(name = SessionConst.LOGIN_USER_ID, required = false) Long loginUserId,
                                   @RequestBody @Valid PostCreateRequest request) {
        checkLogin(loginUserId);
        Long postId = postService.createPost(
                loginUserId,
                request.getTitle(),
                request.getContent());
        return new Result<>(postId);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/posts/{postId}")
    public Result<PostDto> findPost(@PathVariable("postId") Long postId) {
        PostDto post = postService.findPost(postId);
        return new Result<>(post);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/posts/{postId}")
    public void updatePost(@PathVariable("postId") Long postId,
                           @SessionAttribute(name = SessionConst.LOGIN_USER_ID, required = false) Long loginUserId,
                           @RequestBody @Valid PostUpdateRequest request) {
        checkLogin(loginUserId);
        postService.updatePost(
                loginUserId,
                postId,
                request.getTitle(),
                request.getContent());
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable("postId") Long postId,
                           @SessionAttribute(name = SessionConst.LOGIN_USER_ID, required = false) Long loginUserId) {
        checkLogin(loginUserId);
        postService.deletePost(postId, loginUserId);
    }

    private void checkLogin(Long loginUserId) {
        if (Objects.isNull(loginUserId)) {
            throw new AuthenticationException(SessionConst.NO_LOGIN);
        }
    }
}
