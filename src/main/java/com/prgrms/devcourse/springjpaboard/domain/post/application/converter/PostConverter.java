package com.prgrms.devcourse.springjpaboard.domain.post.application.converter;

import java.util.List;
import java.util.stream.Collectors;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostSaveDto;
import com.prgrms.devcourse.springjpaboard.domain.post.application.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.user.User;

public class PostConverter {

	public static Post toPost(PostSaveDto postSaveRequest, User user) {
		return Post.builder()
			.user(user)
			.title(postSaveRequest.getTitle())
			.content(postSaveRequest.getContent())
			.build();
	}

	public static Post toPost(PostUpdateDto postUpdateDto) {
		return Post.builder()
			.title(postUpdateDto.getTitle())
			.content(postUpdateDto.getContent())
			.build();
	}

	public static PostResponseDto toPostResponseDto(Post post) {
		return PostResponseDto.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.build();
	}

	public static PostResponseDtos toPostResponseDtos(List<Post> postList, Long lastIdOfList, boolean hasNext) {
		return PostResponseDtos.builder()
			.postResponseDtoList(postList.stream().map(PostConverter::toPostResponseDto).collect(Collectors.toList()))
			.cursorId(lastIdOfList)
			.hasNext(hasNext)
			.build();
	}

}
