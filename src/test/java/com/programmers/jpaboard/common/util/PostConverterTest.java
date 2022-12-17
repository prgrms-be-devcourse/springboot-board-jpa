package com.programmers.jpaboard.common.util;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.programmers.jpaboard.domain.post.dto.PostCreateRequestDto;
import com.programmers.jpaboard.domain.post.dto.PostResponseDto;
import com.programmers.jpaboard.domain.post.entity.Post;
import com.programmers.jpaboard.domain.post.util.PostConverter;
import com.programmers.jpaboard.domain.user.entity.User;

class PostConverterTest {

	private User user = new User(1L, "권성준", "google@gmail.com", 26, "취미");
	private Post post = new Post(1L, "제목", "내용입니다");

	@Test
	@DisplayName("Post 생성 DTO 를 Post 엔티티로 변환하는 것에 성공한다.")
	void toPost() {
		// given
		PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto("제목", "내용입니다", user.getId());

		// when
		Post convertPost = PostConverter.toPost(postCreateRequestDto, user);

		// then
		assertThat(convertPost.getTitle()).isEqualTo(postCreateRequestDto.getTitle());
		assertThat(convertPost.getContent()).isEqualTo(postCreateRequestDto.getContent());
		assertThat(convertPost.getUser().getId()).isEqualTo(postCreateRequestDto.getUserId());
	}

	@Test
	@DisplayName("Post 엔티티를 Post 응답 DTO 로 변환하는 것에 성공한다.")
	void toPostResponseDto() {
		// given
		post.setUser(user);

		// when
		PostResponseDto postResponseDto = PostConverter.toPostResponseDto(post);

		// then
		assertThat(postResponseDto.getId()).isEqualTo(post.getId());
		assertThat(postResponseDto.getTitle()).isEqualTo(post.getTitle());
		assertThat(postResponseDto.getContent()).isEqualTo(post.getContent());
		assertThat(postResponseDto.getUserId()).isEqualTo(post.getUser().getId());
		assertThat(postResponseDto.getCreatedAt()).isEqualTo(post.getCreatedAt());
		assertThat(postResponseDto.getLastModifiedAt()).isEqualTo(post.getLastModifiedAt());
	}
}