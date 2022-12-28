package com.prgrms.devcourse.springjpaboard.domain.post.application.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateRequest;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostCreateResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponse;
import com.prgrms.devcourse.springjpaboard.domain.post.dto.PostSearchResponses;
import com.prgrms.devcourse.springjpaboard.domain.user.User;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostConverter {

	public Post toPost(PostCreateRequest postSaveRequest, User user) {
		return Post.builder()
			.user(user)
			.title(postSaveRequest.getTitle())
			.content(postSaveRequest.getContent())
			.build();
	}

	public PostSearchResponse toPostSearchResponse(Post post) {
		return PostSearchResponse.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.createdAt(post.getCreatedAt())
			.build();
	}

	public PostSearchResponses toPostSearchResponses(List<Post> postList, Boolean hasNext) {
		return PostSearchResponses.builder()
			.posts(postList.stream().map(this::toPostSearchResponse).collect(Collectors.toList()))
			.hasNext(hasNext)
			.build();
	}

	public PostCreateResponse toPostCreateResponse(Long id) {
		return new PostCreateResponse(id);
	}

}
