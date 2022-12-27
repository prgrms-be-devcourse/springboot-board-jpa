package com.prgrms.devcourse.springjpaboard.domain.post.application;

import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.converter.PostConverter;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequest;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponses;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateRequest;
import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.application.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostFacade {

	private final PostConverter postConverter;

	private final PostService postService;

	private final UserService userService;

	@Transactional
	public PostCreateResponse create(PostCreateRequest postCreateRequest) {

		User user = userService.findById(postCreateRequest.getUserId());

		Long savedId = postService.create(postConverter.toPost(postCreateRequest, user));

		return postConverter.toPostCreateResponse(savedId);

	}

	@Transactional
	public void update(Long id, PostUpdateRequest postUpdateRequest) {
		postService.update(id, postUpdateRequest.getTitle(), postUpdateRequest.getContent());
	}

	public PostSearchResponses findAll(Long cursorId, Integer size) {

		Slice<Post> postSlice = postService.findAll(cursorId, size);

		return new PostSearchResponses(postSlice.getContent(), postSlice.hasNext());
	}

	public PostSearchResponse findById(Long id) {
		return postConverter.toPostResponse(postService.findById(id));
	}
}
