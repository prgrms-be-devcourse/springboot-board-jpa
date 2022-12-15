package com.prgrms.devcourse.springjpaboard.domain.post.service.facade;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.service.PostService;
import com.prgrms.devcourse.springjpaboard.domain.post.service.converter.PostConverter;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostSaveDto;
import com.prgrms.devcourse.springjpaboard.domain.post.service.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.service.UserService;

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

		return PostResponseDtos.builder()
			.postResponseDtoList(postList.stream().map(PostConverter::toPostResponseDto).collect(Collectors.toList()))
			.cursorId(lastIdOfList)
			.hasNext(hasNext)
			.build();

	}

	public PostResponseDto findById(Long id) {
		return PostConverter.toPostResponseDto(postService.findById(id));
	}
}
