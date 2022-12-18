package com.prgrms.devcourse.springjpaboard.domain.post.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.converter.PostConverter;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostSaveDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.application.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostFacade {

	private final PostService postService;

	private final UserService userService;

	public void create(PostSaveDto postSaveDto) {

		User user = userService.findById(postSaveDto.getUserId());

		postService.create(PostConverter.toPost(postSaveDto, user));

	}

	public void update(Long id, PostUpdateDto postUpdateDto) {

		postService.update(id, PostConverter.toPost(postUpdateDto));

	}

	public PostResponseDtos findAll(PostRequestDto postRequestDto) {

		List<Post> postList = postService.findAll(postRequestDto.getCursorId(), postRequestDto.getSize());

		Long lastIdOfList = postService.getLastIdOfList(postList);

		boolean hasNext = postService.hasNext(lastIdOfList);

		return PostConverter.toPostResponseDtos(postList, lastIdOfList, hasNext);

	}

	public PostResponseDto findById(Long id) {
		return PostConverter.toPostResponseDto(postService.findById(id));
	}
}
