package com.prgrms.boardjpa.application.post.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.prgrms.boardjpa.application.post.PostDto;
import com.prgrms.boardjpa.application.post.service.PostService;
import com.prgrms.boardjpa.core.commons.api.SuccessResponse;
import com.prgrms.boardjpa.core.commons.page.SimplePage;

@RequestMapping("/api/posts")
@RestController
public class PostRestController {
	private final PostService postService;

	public PostRestController(PostService postService) {
		this.postService = postService;
	}

	@GetMapping
	public ResponseEntity<SuccessResponse<List<PostDto.PostInfo>>> getPostsByPaging(
		@ModelAttribute SimplePage pageable,
		BindingResult bindingResult
	) {
		PageRequest pageRequest = SimplePage.defaultPageRequest();

		if (bindingResult.hasErrors() == false) {
			pageRequest = PageRequest.of(pageable.getPage(), pageable.getSize());
		}

		return createSuccessResponse(
			postService.getAllByPaging(pageRequest),
			HttpStatus.OK);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<SuccessResponse<PostDto.PostInfo>> store(
		@RequestBody @Valid PostDto.CreatePostRequest createRequest) {

		return createSuccessResponse(
			postService.store(
				createRequest.title(),
				createRequest.writerId(),
				createRequest.content()),
			HttpStatus.CREATED
		);
	}

	@PutMapping("/{postId}")
	public ResponseEntity<SuccessResponse<PostDto.PostInfo>> edit(@PathVariable Long postId,
		@RequestBody @Valid PostDto.UpdatePostRequest updateRequest) {

		return createSuccessResponse(
			postService.edit(
				updateRequest.title(),
				postId,
				updateRequest.content()),
			HttpStatus.OK
		);
	}

	@GetMapping("/{postId}")
	public ResponseEntity<SuccessResponse<PostDto.PostInfo>> showOne(@PathVariable Long postId) {
		return createSuccessResponse(
			postService.getById(postId),
			HttpStatus.OK
		);
	}

	// TODO : 상속을 이용해 풀어내야 할 필요성이 있을까?
	private <T> ResponseEntity<SuccessResponse<T>> createSuccessResponse(T body, HttpStatus status) {
		return new ResponseEntity<>(
			SuccessResponse.of(body),
			status
		);
	}
}
