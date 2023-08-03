package prgms.boardmission.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import prgms.boardmission.ApiResponse;
import prgms.boardmission.post.dto.PostDto;
import prgms.boardmission.post.service.PostService;

import java.util.NoSuchElementException;

@RestController
public class PostController {
    private final PostService postService;

    @ExceptionHandler(NoSuchElementException.class)
    public ApiResponse<String> notFoundHandler(NoSuchElementException e) {
        return ApiResponse.fail(404, e.getMessage());
    }

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public ApiResponse<Long> save(@RequestBody PostDto postDto) {
        Long id = postService.save(postDto);
        return ApiResponse.ok(id);
    }

    @GetMapping("/posts")
    public ApiResponse<Page<PostDto>> findAll(Pageable pageable) {
        Page<PostDto> postDtoPage = postService.findAll(pageable);
        return ApiResponse.ok(postDtoPage);
    }

    @GetMapping("/posts/{postId}")
    public ApiResponse<PostDto> findById(@PathVariable long postId) {
        PostDto postDto = postService.findById(postId);
        return ApiResponse.ok(postDto);
    }
}
