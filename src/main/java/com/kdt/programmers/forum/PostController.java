package com.kdt.programmers.forum;

import com.kdt.programmers.forum.transfer.ApiResponse;
import com.kdt.programmers.forum.transfer.PostDto;
import javassist.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    @ExceptionHandler(NotFoundException.class)
    public ApiResponse<String> notFoundHandler(NotFoundException e) {
        return ApiResponse.notFound("not found");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<String> badRequestException(IllegalArgumentException e) {
        return ApiResponse.badRequest("bad request");
    }

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("")
    public ApiResponse<PostDto> savePost(@RequestBody PostDto dto) throws NotFoundException {
        Long postId = postService.savePost(dto);
        PostDto post = postService.findPostById(postId);
        return ApiResponse.created(post);
    }
}
