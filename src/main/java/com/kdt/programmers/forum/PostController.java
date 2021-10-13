package com.kdt.programmers.forum;

import com.kdt.programmers.forum.transfer.ApiResponse;
import com.kdt.programmers.forum.transfer.PostDto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.response(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<String> badRequestException(IllegalArgumentException e) {
        return ApiResponse.response(e.getMessage());
    }

    @GetMapping("")
    public ApiResponse<Page<PostDto>> getPosts(Pageable pageable) {
        Page<PostDto> posts = postService.findPostsByPage(pageable);
        return ApiResponse.response(posts);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostDto> getPost(@PathVariable Long postId) throws NotFoundException {
        PostDto post = postService.findPostById(postId);
        return ApiResponse.response(post);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public ApiResponse<PostDto> savePost(@RequestBody PostDto dto) {
        PostDto post = postService.savePost(dto);
        return ApiResponse.response(post);
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto dto) throws NotFoundException {
        PostDto post = postService.updatePost(postId, dto);
        return ApiResponse.response(post);
    }
}
