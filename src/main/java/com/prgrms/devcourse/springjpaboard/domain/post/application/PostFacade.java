package com.prgrms.devcourse.springjpaboard.domain.post.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.devcourse.springjpaboard.domain.post.application.converter.PostConverter;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.CursorResult;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateDto;
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
	public PostCreateResponseDto create(PostCreateRequestDto postSaveDto) {

		User user = userService.findById(postSaveDto.getUserId());

		Long savedId = postService.create(postConverter.toPost(postSaveDto, user));

		return postConverter.toPostCreateResponseDto(savedId);

	}

	@Transactional
	public void update(Long id, PostUpdateDto postUpdateDto) {

		postService.update(id, postUpdateDto.getTitle(), postUpdateDto.getContent());

	}

	public PostResponseDtos findAll(PostRequestDto postRequestDto) {
		CursorResult cursorResult = postService.findAll(postRequestDto.getCursorId(), postRequestDto.getSize());
		return postConverter.toPostResponseDtos(cursorResult);
	}

	public PostResponseDto findById(Long id) {
		return postConverter.toPostResponseDto(postService.findById(id));
	}
}
