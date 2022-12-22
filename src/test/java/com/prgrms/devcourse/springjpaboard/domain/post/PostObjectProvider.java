package com.prgrms.devcourse.springjpaboard.domain.post;

import java.util.List;
import java.util.stream.Collectors;

import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostRequestDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDto;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostResponseDtos;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateDto;
import com.prgrms.devcourse.springjpaboard.domain.user.User;

public class PostObjectProvider {

	public static Post createPost(User user) {
		return createPost("hello", "hi", user);
	}

	public static Post createPost(String title, String content) {
		return createPost(title, content, null);
	}

	public static Post createPost(String title, String content, User user) {
		return Post.builder()
			.user(user)
			.title(title)
			.content(content)
			.build();
	}


	public static PostCreateRequestDto createPostCreateRequestDto(Long userId) {
		return PostCreateRequestDto.builder()
			.userId(userId)
			.title("제목")
			.content("내용")
			.build();
	}

	public static PostCreateResponseDto createPostCreteResponseDto(Long postId) {
		return PostCreateResponseDto.builder()
			.id(postId)
			.build();
	}

	public static PostUpdateDto createPostUpdateDto() {
		return PostUpdateDto.builder()
			.title("수정한 제목")
			.content("수정한 내용")
			.build();
	}

	public static PostResponseDto createPostResponseDto(Long postId) {
		return PostResponseDto.builder()
			.id(postId)
			.title("제목")
			.content("내용")
			.build();
	}

	public static PostRequestDto createPostRequestDto(Long cursorId, Integer size) {
		return PostRequestDto.builder()
			.cursorId(cursorId)
			.size(size)
			.build();
	}

	public static PostResponseDtos createPostResponseDtos(List<Post> postList, Long lastIdOfList, boolean hasNext) {
		return PostResponseDtos.builder()
			.postResponseDtoList(postList.stream().map(a->PostResponseDto.builder()
				.id(a.getId())
				.title(a.getTitle())
				.content(a.getContent())
				.build()).collect(Collectors.toList()))
			.cursorId(lastIdOfList)
			.hasNext(hasNext)
			.build();
	}

}
