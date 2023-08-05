package com.programmers.heheboard.domain.post;

import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.heheboard.global.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/posts")
public class PostController {

	private final PostService postService;

	@ExceptionHandler(Exception.class)
	public ApiResponse<String> internalServerErrorHandler(Exception e) {
		return ApiResponse.fail(500, e.getMessage());
	}

	@PostMapping
	public ApiResponse<PostResponseDto> save(@RequestBody CreatePostRequestDto createPostRequestDto) {
		PostResponseDto postDto = postService.createPost(createPostRequestDto);
		return ApiResponse.ok(postDto);
	}

	@PutMapping("/{post-id}")
	public ApiResponse<PostResponseDto> updatePost(@RequestBody UpdatePostRequestDto updatePostRequestDto,
		@PathVariable("post-id") Long postId) {
		PostResponseDto postResponseDTO = postService.updatePost(postId, updatePostRequestDto);
		return ApiResponse.ok(postResponseDTO);
	}

	@GetMapping("/{post-id}")
	public ApiResponse<PostResponseDto> getOnePost(@PathVariable("post-id") Long postId) {
		PostResponseDto postDto = postService.findPost(postId);
		return ApiResponse.ok(postDto);
	}

	@GetMapping
	public ApiSliceResponse getPostBySlice(@RequestParam int page, @RequestParam int size) {
		Slice<PostResponseDto> postSliceResponseDtos = postService.getPosts(page, size);
		return ApiSliceResponse.ok(postSliceResponseDtos);
	}
}
