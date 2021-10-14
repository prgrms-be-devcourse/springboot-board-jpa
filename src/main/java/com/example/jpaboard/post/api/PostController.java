package com.example.jpaboard.post.api;

import com.example.jpaboard.post.converter.PostConverter;
import com.example.jpaboard.post.domain.Post;
import com.example.jpaboard.post.dto.PostDto;
import com.example.jpaboard.user.domain.User;
import com.example.jpaboard.common.ApiResponse;
import com.example.jpaboard.post.dto.PostRequest;
import com.example.jpaboard.post.dto.PostResponse;
import com.example.jpaboard.exception.PostNotFoundException;
import com.example.jpaboard.exception.UserNotFoundException;
import com.example.jpaboard.user.infra.UserRepository;
import com.example.jpaboard.post.application.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<PostDto> getPostDetails(@PathVariable Long postId) {
        return ResponseEntity.ok(postService.getPost(postId));
    }

    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        Long userId = postRequest.getAuthorId();

        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        Post post = postService.createPost(user, postRequest);

        return ApiResponse.ok(PostConverter.convertPostResponse(post));
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
