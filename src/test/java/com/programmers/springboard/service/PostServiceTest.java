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
import com.programmers.springboard.repository.MemberRepository;
import com.programmers.springboard.repository.PostCustomRepository;
import com.programmers.springboard.repository.PostRepository;
import com.programmers.springboard.request.PostCreateRequest;
import com.programmers.springboard.request.PostUpdateRequest;
import com.programmers.springboard.response.PostResponse;

class PostServiceTest {

	@Mock
	private PostCustomRepository postCustomRepository;
	@Mock
	private PostRepository postRepository;
	@Mock
	private MemberRepository memberRepository;

	@InjectMocks
	private PostService postService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void 포스트를_작성합니다() {
		// Given
		PostCreateRequest request = new PostCreateRequest("test", "test", 0L);
		Member member = Member.builder().id(1L).build();
		Post post = new Post(1L, "title", "content", false, member);

		when(postRepository.save(any())).thenReturn(post);
		when(memberRepository.findById(any())).thenReturn(Optional.of(member));

		// When
		PostResponse response = postService.createPost(request);

		// Then
		assertThat(response.postId(), Matchers.is(post.getId()));
	}

	@Test
	void 포스트를_수정합니다() {
		// Given
		PostUpdateRequest request = new PostUpdateRequest("test", "test");
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
		Post post1 = new Post(1L, "title", "content", false, member);
		Post post2 = new Post(1L, "title", "content", false, member);

		List<Post> posts = List.of(post1, post2);
		when(postRepository.findAllById(any())).thenReturn(posts);

		// When
		List<Long> ids = List.of(post1.getId(), post2.getId());
		postService.deletePosts(ids);

		// Then
		verify(postRepository).deleteAll(posts);
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
		PostUpdateRequest request = new PostUpdateRequest("test", "test");

		when(postRepository.findById(any())).thenReturn(Optional.empty());

		// when // then
		assertThrows(PostNotFoundException.class, () -> {
			postService.updatePost(post.getId(), request);
		});
	}

}
