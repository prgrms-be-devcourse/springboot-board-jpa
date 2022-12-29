package com.programmers.jpaboard.web.post;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.jpaboard.domain.post.dto.PostCreateRequestDto;
import com.programmers.jpaboard.domain.post.dto.PostResponseDto;
import com.programmers.jpaboard.domain.post.dto.PostResponseDtoList;
import com.programmers.jpaboard.domain.post.dto.PostUpdateRequestDto;
import com.programmers.jpaboard.domain.post.service.PostService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostApiController {

	private final PostService postService;

	@PostMapping
	ResponseEntity<PostResponseDto> createPost(@RequestBody @Valid PostCreateRequestDto postCreateRequestDto) {
		PostResponseDto createdPostDto = postService.createPost(postCreateRequestDto);
		return new ResponseEntity<>(createdPostDto, HttpStatus.CREATED);
	}

	@GetMapping
	ResponseEntity<PostResponseDtoList> getPosts(Pageable pageable) {
		PostResponseDtoList posts = postService.getPosts(pageable);
		return ResponseEntity.ok(posts);
	}

	@GetMapping("/{postId}")
	ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId) {
		PostResponseDto findPostDto = postService.getPostById(postId);
		return ResponseEntity.ok(findPostDto);
	}

	@PatchMapping("/{postId}")
	ResponseEntity<PostResponseDto> updatePost(
		@PathVariable Long postId,
		@RequestBody @Valid PostUpdateRequestDto postUpdateRequestDto
	) {
		PostResponseDto updatedPostDto = postService.updatePost(postId, postUpdateRequestDto);
		return ResponseEntity.ok(updatedPostDto);
	}
}
