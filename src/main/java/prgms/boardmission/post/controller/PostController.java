package prgms.boardmission.post.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import prgms.boardmission.ApiResponse;
import prgms.boardmission.post.dto.PostDto;
import prgms.boardmission.post.dto.PostUpdateDto;
import prgms.boardmission.post.service.PostService;

import java.util.NoSuchElementException;

@RequestMapping("/posts")
@RestController
public class PostController {
    private final PostService postService;

    @ExceptionHandler(NoSuchElementException.class)
    public ApiResponse<String> notFoundHandler(NoSuchElementException e) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND, e.getMessage());
    }

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/")
    public ApiResponse<Long> save(@RequestBody PostDto postDto) {
        Long id = postService.save(postDto);
        return ApiResponse.ok(id);
    }

    @GetMapping("/")
    public ApiResponse<Page<PostDto>> findAll(Pageable pageable) {
        Page<PostDto> postDtoPage = postService.findAll(pageable);
        return ApiResponse.ok(postDtoPage);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostDto> findById(@PathVariable long postId) {
        PostDto postDto = postService.findById(postId);
        return ApiResponse.ok(postDto);
    }

    @PatchMapping("/{postId}")
    public ApiResponse<Long> update(@PathVariable long postId, PostUpdateDto postUpdateDto) {
        Long updateId = postService.updatePost(postId, postUpdateDto);
        return ApiResponse.ok(updateId);
    }
}
