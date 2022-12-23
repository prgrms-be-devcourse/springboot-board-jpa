package com.prgrms.devcourse.springjpaboard.domain.post;

import java.util.List;
import java.util.stream.Collectors;

import com.prgrms.devcourse.springjpaboard.domain.post.dto.CursorResult;
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

		return new PostCreateRequestDto(userId, "제목", "내용");

	}

	public static PostCreateResponseDto createPostCreteResponseDto(Long postId) {
		return PostCreateResponseDto.builder()
			.id(postId)
			.build();
	}

	public static PostUpdateDto createPostUpdateDto() {
		return new PostUpdateDto("수정한 제목", "수정한 내용");
	}

	public static PostResponseDto createPostResponseDto(Long postId) {
		return PostResponseDto.builder()
			.id(postId)
			.title("제목")
			.content("내용")
			.build();
	}

	public static PostRequestDto createPostRequestDto(Long cursorId, Integer size) {
		return new PostRequestDto(cursorId, size);
	}

	public static CursorResult createCursorResult(List<Post> postList, Long nextCursorId, boolean hasNext) {
		return CursorResult.builder()
			.postList(postList)
			.nextCursorId(nextCursorId)
			.hasNext(hasNext)
			.build();
	}

	public static PostResponseDtos createPostResponseDtos(CursorResult cursorResult) {
		return PostResponseDtos.builder()
			.postResponseDtoList(cursorResult.getPostList().stream().map(a -> PostResponseDto.builder()
				.id(a.getId())
				.title(a.getTitle())
				.content(a.getContent())
				.build()).collect(Collectors.toList()))
			.nextCursorId(cursorResult.getNextCursorId())
			.hasNext(cursorResult.getHasNext())
			.build();
	}

}
