package com.example.jpaboard.controller;

import com.example.jpaboard.domain.Post;
import com.example.jpaboard.domain.User;
import com.example.jpaboard.dto.ApiResponse;
import com.example.jpaboard.dto.PostRequest;
import com.example.jpaboard.dto.PostResponse;
import com.example.jpaboard.exception.PostNotFoundException;
import com.example.jpaboard.exception.UserNotFoundException;
import com.example.jpaboard.repository.UserRepository;
import com.example.jpaboard.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final UserRepository userRepository;

    @GetMapping("{postId}")
    public ApiResponse<PostResponse> getPostDetails(@PathVariable Long postId) {
        Post findPost = postService.getPost(postId);
        Long id = findPost.getId();
        String title = findPost.getTitle();
        String content = findPost.getContent();
        String author = findPost.getAuthor().getName();
        return ApiResponse.ok(new PostResponse(id, title, content, author));
    }

    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        Long userId = postRequest.getAuthorId();

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Post post = postService.createPost(user, postRequest);

        Long id = post.getId();
        String title = post.getTitle();
        String content = post.getContent();
        String author = post.getAuthor().getName();

        return ApiResponse.ok(new PostResponse(id, title, content, author));
    }

    @PostMapping("{postId}")
    public ApiResponse<PostResponse> updatePost(@PathVariable Long postId,
        @RequestBody PostRequest postRequest) {

        Post updated = postService.update(postId, postRequest);

        Long id = updated.getId();
        String title = updated.getTitle();
        String content = updated.getContent();
        String author = updated.getAuthor().getName();

        return ApiResponse.ok(new PostResponse(id, title, content, author));
    }

    @GetMapping()
    public ApiResponse<Page<PostResponse>> getPosts(Pageable pageable) {
        return ApiResponse.ok(postService.getPosts(pageable));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void postNotFoundHandler(PostNotFoundException exception) {
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void userNotFoundHandler(UserNotFoundException exception) {
    }
}
