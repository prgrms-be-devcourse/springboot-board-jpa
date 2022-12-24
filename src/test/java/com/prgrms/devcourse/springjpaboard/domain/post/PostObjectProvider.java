package com.prgrms.devcourse.springjpaboard.domain.post;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.data.domain.Slice;

import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequest;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponses;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostUpdateRequest;
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

	public static List<Post> createPostList(User user) {
		return IntStream.range(0, 20)
			.mapToObj(value ->
				new Post("title" + value, "content" + value, user)
			).toList();
	}

	public static PostCreateRequest createPostCreateRequest(Long userId) {

		return new PostCreateRequest(userId, "제목", "내용");

	}

	public static PostCreateResponse createPostCreteResponse(Long postId) {
		return PostCreateResponse.builder()
			.id(postId)
			.build();
	}

	public static PostUpdateRequest createPostUpdateRequest() {
		return new PostUpdateRequest("수정한 제목", "수정한 내용");
	}

	public static PostSearchResponse createPostSearchResponse(Long postId) {
		return PostSearchResponse.builder()
			.id(postId)
			.title("제목")
			.content("내용")
			.build();
	}

	public static PostSearchResponses createPostSearchResponses(List<Post> postList, boolean hasNext) {
		return new PostSearchResponses(postList, hasNext);
	}

}
