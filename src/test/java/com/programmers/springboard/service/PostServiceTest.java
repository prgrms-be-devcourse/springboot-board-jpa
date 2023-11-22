package com.programmers.springboard.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.entity.Post;
import com.programmers.springboard.exception.PostNotFoundException;
import com.programmers.springboard.repository.PostCustomRepository;
import com.programmers.springboard.repository.PostRepository;
import com.programmers.springboard.request.CreatePostRequest;
import com.programmers.springboard.request.UpdatePostRequest;
import com.programmers.springboard.response.PostResponse;

class PostServiceTest {

	@Mock
	private PostCustomRepository postCustomRepository;
	@Mock
	private PostRepository postRepository;
	@Mock
	private MemberService memberService;

	@InjectMocks
	private PostService postService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void 포스트를_작성합니다() {
		// Given
		CreatePostRequest request = new CreatePostRequest("test", "test", 0L);
		Member member = Member.builder().id(1L).build();
		Post post = new Post(1L, "title", "content", false, member);

		when(postRepository.save(any())).thenReturn(post);
		when(memberService.getMemberById(any())).thenReturn(member);

		// When
		PostResponse response = postService.createPost(request);

		// Then
		assertThat(response.postId(), Matchers.is(post.getId()));
	}

	@Test
	void 포스트를_수정합니다() {
		// Given
		UpdatePostRequest request = new UpdatePostRequest("test", "test");
		Member member = Member.builder().id(1L).build();
		Post post = new Post(1L, "title", "content", false, member);

		when(postRepository.findById(any())).thenReturn(Optional.of(post));

		// When
		PostResponse response = postService.updatePost(post.getId(), request);

		// Then
		assertThat(response.title(), Matchers.is("test"));
		assertThat(response.content(), Matchers.is("test"));
	}

	@Test
	void 포스트를_삭제합니다() {
		// Given
		Member member = Member.builder().id(1L).build();
		Post post = new Post(1L, "title", "content", false, member);

		when(postRepository.findById(any())).thenReturn(Optional.of(post));

		// When
		postService.deletePost(post.getId());

		// Then
		verify(postRepository).delete(any());
	}

	@Test
	void 포스트를_페이지_조회합니다() {
		// Given
		Member member = Member.builder().id(1L).build();
		Post post = new Post(1L, "title", "content", false, member);

		when(postCustomRepository.getPosts(any())).thenReturn(List.of(post, post, post));

		// When
		List<PostResponse> responses = postService.getPosts(1);

		// Then
		verify(postCustomRepository).getPosts(any());
		assertThat(responses, hasSize(3));
	}

	@Test
	void 포스트를_단건_조회합니다() {
		// Given
		Member member = Member.builder().id(1L).build();
		Post post = new Post(1L, "title", "content", false, member);

		when(postRepository.findById(any())).thenReturn(Optional.of(post));

		// When
		PostResponse response = postService.getPostById(post.getId());

		// Then
		assertThat(response.postId(), is(post.getId()));
		assertThat(response.title(), is(post.getTitle()));
		assertThat(response.content(), is(post.getContent()));
	}

	@Test
	void 존재하지_않는_포스트를_조회합니다_실패() {
		// given
		Member member = Member.builder().id(1L).build();
		Post post = new Post(1L, "title", "content", false, member);

		when(postRepository.findById(any())).thenReturn(Optional.empty());

		// when // then
		assertThrows(PostNotFoundException.class, () -> {
			postService.getPostById(post.getId());
		});
	}

	@Test
	void 존재하지_않는_포스트를_수정합니다_실패() {
		// given
		Member member = Member.builder().id(1L).build();
		Post post = new Post(1L, "title", "content", false, member);
		UpdatePostRequest request = new UpdatePostRequest("test", "test");

		when(postRepository.findById(any())).thenReturn(Optional.empty());

		// when // then
		assertThrows(PostNotFoundException.class, () -> {
			postService.updatePost(post.getId(), request);
		});
	}

	@Test
	void 존재하지_않는_포스트를_삭제합니다_실패() {
		// given
		Member member = Member.builder().id(1L).build();
		Post post = new Post(1L, "title", "content", false, member);

		when(postRepository.findById(any())).thenReturn(Optional.empty());

		// when // then
		assertThrows(PostNotFoundException.class, () -> {
			postService.deletePost(post.getId());
		});
	}
}
