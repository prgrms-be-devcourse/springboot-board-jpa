package com.prgrms.devcourse.springjpaboard.domain.post.application.converter;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.CursorResult;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.user.User;

@Component
public class PostConverter {

	public Post toPost(PostCreateRequestDto postSaveRequest, User user) {
		return Post.builder()
			.user(user)
			.title(postSaveRequest.getTitle())
			.content(postSaveRequest.getContent())
			.build();
	}

	public Post toPost(PostUpdateDto postUpdateDto) {
		return Post.builder()
			.title(postUpdateDto.getTitle())
			.content(postUpdateDto.getContent())
			.build();
	}

	public PostResponseDto toPostResponseDto(Post post) {
		return PostResponseDto.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.createdAt(post.getCreatedAt())
			.build();
	}

	public PostResponseDtos toPostResponseDtos(CursorResult cursorResult) {
		return PostResponseDtos.builder()
			.postResponseDtoList(
				cursorResult.getPostList().stream().map(this::toPostResponseDto).collect(Collectors.toList()))
			.nextCursorId(cursorResult.getNextCursorId())
			.hasNext(cursorResult.getHasNext())
			.build();
	}

	public PostCreateResponseDto toPostCreateResponseDto(Long id) {
		return PostCreateResponseDto.builder()
			.id(id)
			.build();
	}

}
