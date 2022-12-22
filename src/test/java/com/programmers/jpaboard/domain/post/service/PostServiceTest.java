package com.programmers.jpaboard.domain.post.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.programmers.jpaboard.common.exception.PostNotFoundException;
import com.programmers.jpaboard.domain.post.dto.PostCreateRequestDto;
import com.programmers.jpaboard.domain.post.dto.PostResponseDto;
import com.programmers.jpaboard.domain.post.dto.PostUpdateRequestDto;
import com.programmers.jpaboard.domain.post.entity.Post;
import com.programmers.jpaboard.domain.post.repository.PostRepository;
import com.programmers.jpaboard.domain.user.entity.User;
import com.programmers.jpaboard.domain.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

	@InjectMocks
	PostService postService;

	@Mock
	PostRepository postRepository;

	@Mock
	UserRepository userRepository;

	private User user = new User(1L, "권성준", "naver@naver.com", 26, "취미");
	private Post post = new Post(1L, "제목", "내용입니다");

	@Test
	@DisplayName("게시물 생성에 성공한다.")
	void successCreatePost() {
		// given
		post.setUser(user);
		PostCreateRequestDto postCreateRequestDto = new PostCreateRequestDto("제목", "내용입니다.", user.getId());
		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		when(postRepository.save(any(Post.class))).thenReturn(post);

		// when
		PostResponseDto savedPostDto = postService.createPost(postCreateRequestDto);

		// then
		assertThat(savedPostDto)
			.hasFieldOrPropertyWithValue("id", post.getId())
			.hasFieldOrPropertyWithValue("title", post.getTitle())
			.hasFieldOrPropertyWithValue("content", post.getContent())
			.hasFieldOrPropertyWithValue("userId", post.getUser().getId())
			.hasFieldOrPropertyWithValue("createdAt", post.getCreatedAt())
			.hasFieldOrPropertyWithValue("lastModifiedAt", post.getLastModifiedAt());
		verify(userRepository, times(1)).findById(user.getId());
		verify(postRepository, times(1)).save(any(Post.class));
	}

	@Test
	@DisplayName("게시물 아이디로 조회에 성공한다.")
	void getPostById() {
		// given
		post.setUser(user);
		when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

		// when
		PostResponseDto findPostDto = postService.getPostById(post.getId());

		// then
		assertThat(findPostDto)
			.hasFieldOrPropertyWithValue("id", post.getId())
			.hasFieldOrPropertyWithValue("title", post.getTitle())
			.hasFieldOrPropertyWithValue("content", post.getContent())
			.hasFieldOrPropertyWithValue("userId", post.getUser().getId());
		verify(postRepository, times(1)).findById(post.getId());
	}

	@Test
	@DisplayName("게시물 아이디로 조회에 실패한다.")
	void failGetPostById() {
		// given
		Post noIdPost = new Post("제목", "내용입니다.");
		when(postRepository.findById(noIdPost.getId())).thenReturn(Optional.empty());

		// when, then
		assertThatThrownBy(() -> postService.getPostById(noIdPost.getId()))
			.isInstanceOf(PostNotFoundException.class);
		verify(postRepository, times(1)).findById(noIdPost.getId());
	}

	@Test
	@DisplayName("게시물 수정에 성공한다.")
	void updatePost() {
		// given
		post.setUser(user);
		String updateTitle = "수정된 제목";
		String updateContent = "수정된 내용입니다.";
		PostUpdateRequestDto postUpdateRequestDto = new PostUpdateRequestDto(updateTitle, updateContent);
		when(postRepository.findById(post.getId())).thenReturn(Optional.of(post));

		// when
		PostResponseDto updatedPostDto = postService.updatePost(post.getId(), postUpdateRequestDto);

		// then
		assertThat(updatedPostDto)
			.hasFieldOrPropertyWithValue("id", post.getId())
			.hasFieldOrPropertyWithValue("title", updateTitle)
			.hasFieldOrPropertyWithValue("content", updateContent)
			.hasFieldOrPropertyWithValue("userId", post.getUser().getId());
		assertThat(post)
			.hasFieldOrPropertyWithValue("title", updateTitle)
			.hasFieldOrPropertyWithValue("content", updateContent);
		verify(postRepository, times(1)).findById(post.getId());
	}
}