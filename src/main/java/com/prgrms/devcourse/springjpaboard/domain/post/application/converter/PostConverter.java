package com.prgrms.devcourse.springjpaboard.domain.post.application.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.application.PostService;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.user.User;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

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
			.build();
	}

	public PostResponseDtos toPostResponseDtos(List<Post> postList, Long lastIdOfList, boolean hasNext) {
		return PostResponseDtos.builder()
			.postResponseDtoList(postList.stream().map(this::toPostResponseDto).collect(Collectors.toList()))
			.cursorId(lastIdOfList)
			.hasNext(hasNext)
			.build();
	}

	public PostCreateResponseDto toPostCreateResponseDto(Long id) {
		return PostCreateResponseDto.builder()
			.id(id)
			.build();
	}

}
