package kdt.cse0518.board.post.controller;

import kdt.cse0518.board.common.api.ApiResponse;
import kdt.cse0518.board.post.converter.PostConverter;
import kdt.cse0518.board.post.dto.PostDto;
import kdt.cse0518.board.post.dto.RequestDto;
import kdt.cse0518.board.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final PostConverter converter;

    public PostController(final PostService postService, final PostConverter converter) {
        this.postService = postService;
        this.converter = converter;
    }

    @ExceptionHandler
    private ApiResponse<String> exceptionHandle(final Exception exception) {
        return ApiResponse.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    private ApiResponse<String> notFoundHandle(final NullPointerException exception) {
        return ApiResponse.fail(HttpStatus.NOT_FOUND.value(), exception.getMessage());
    }

    @GetMapping
    public ApiResponse<Page<RequestDto>> getPosts(final Pageable pageable) {
        final Page<RequestDto> postDto = postService.findAll(pageable).map(converter::toRequestDto);
        return ApiResponse.ok(postDto);
    }

    @GetMapping("/{postId}")
    public ApiResponse<RequestDto> getPost(@PathVariable final Long postId) {
        return ApiResponse.ok(converter.toRequestDto(postService.findById(postId)));
    }

//    @GetMapping("/{userId}")
//    public Page<PostDto> getPostsByUserId(final UserDto userDto, final Pageable pageable) {
//        return postService.findAllByUser(userDto, pageable);
//    }

    @PostMapping
    public ApiResponse<Long> insert(@RequestBody final PostDto postDto) {
        return ApiResponse.ok(postService.newRequestDtoSave(postDto));
    }

    @PutMapping("/{postId}")
    public ApiResponse<Long> update(
            @PathVariable final Long postId,
            @RequestBody final PostDto postDto
    ) throws NullPointerException {
        return ApiResponse.ok(postService.update(postDto));
    }
}
