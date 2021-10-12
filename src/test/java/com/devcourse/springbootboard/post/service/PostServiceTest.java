package com.devcourse.springbootboard.post.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.devcourse.springbootboard.post.converter.PostConverter;
import com.devcourse.springbootboard.post.domain.Post;
import com.devcourse.springbootboard.post.dto.PostDeleteResponse;
import com.devcourse.springbootboard.post.dto.PostResponse;
import com.devcourse.springbootboard.post.dto.PostUpdateRequest;
import com.devcourse.springbootboard.post.dto.PostWriteRequest;
import com.devcourse.springbootboard.post.repository.PostRepository;
import com.devcourse.springbootboard.user.domain.Hobby;
import com.devcourse.springbootboard.user.domain.User;
import com.devcourse.springbootboard.user.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
	private static final User USER = new User(1L, "samkimuel", 29, new Hobby("football"));

	@Mock
	private PostRepository postRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private PostConverter postConverter;

	@InjectMocks
	private PostService postService;

	@DisplayName("게시글 페이징 조회 테스트")
	@Test
	void testFindPosts() {
		// given
		Pageable pageable = PageRequest.of(0, 5);
		List<Post> posts = new ArrayList<>();
		for (int i = 1; i < 4; i++) {
			posts.add(new Post((long)i, "title", "content", USER));
		}
		Page<Post> postPage = new PageImpl<>(posts);
		given(postRepository.findAll(pageable)).willReturn(postPage);
		PostResponse mPostResponse = new PostResponse(1L, "title", "content", USER.getId());
		given(postConverter.toPostResponse(any(Post.class))).willReturn(mPostResponse);

		// when
		Page<PostResponse> postResponses = postService.findPosts(pageable);

		// then
		assertThat(postResponses.getTotalPages()).isEqualTo(postPage.getTotalPages());
		assertThat(postResponses.getSize()).isEqualTo(postPage.getSize());
		assertThat(postResponses.getTotalElements()).isEqualTo(3);
	}

	@DisplayName("게시글 상세 조회 테스트")
	@Test
	void testFindPost() {
		// given
		Post post = new Post(1L, "title", "content", USER);
		given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
		PostResponse mPostResponse = new PostResponse(1L, "title", "content", USER.getId());
		given(postConverter.toPostResponse(post)).willReturn(mPostResponse);

		// when
		PostResponse postResponse = postService.findPost(1L);

		// then
		assertThat(postResponse).isNotNull()
			.hasFieldOrPropertyWithValue("postId", 1L)
			.hasFieldOrPropertyWithValue("title", "title")
			.hasFieldOrPropertyWithValue("content", "content")
			.hasFieldOrPropertyWithValue("userId", 1L);
	}

	@DisplayName("게시글 작성 테스트")
	@Test
	void testSavePost() {
		// given
		given(userRepository.findById(anyLong())).willReturn(Optional.of(USER));
		PostWriteRequest mPostWriteRequest = new PostWriteRequest("제목", "내용", USER.getId());
		Post mPost = new Post(2L, "제목", "내용", USER);
		given(postConverter.convertPostWriteRequest(mPostWriteRequest, USER)).willReturn(mPost);
		given(postRepository.save(any(Post.class))).willReturn(mPost);
		PostResponse mPostResponse = new PostResponse(2L, "제목", "내용", USER.getId());
		given(postConverter.toPostResponse(any(Post.class))).willReturn(mPostResponse);

		// when
		PostResponse postResponse = postService.savePost(mPostWriteRequest);

		// then
		assertThat(postResponse).isNotNull()
			.hasFieldOrPropertyWithValue("title", "제목")
			.hasFieldOrPropertyWithValue("content", "내용")
			.hasFieldOrPropertyWithValue("userId", 1L);
	}

	@DisplayName("게시글 수정 테스트")
	@Test
	void testModifyPost() {
		// given
		Post post = new Post(2L, "title", "content", USER);
		PostUpdateRequest postUpdateRequest = new PostUpdateRequest(2L, "타이틀수정", "본문수정", USER.getId());
		given(userRepository.existsById(anyLong())).willReturn(true);
		given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
		PostResponse mPostResponse = new PostResponse(2L, "타이틀수정", "본문수정", USER.getId());
		given(postConverter.toPostResponse(any(Post.class))).willReturn(mPostResponse);

		// when
		PostResponse postResponse = postService.updatePost(postUpdateRequest);

		// then
		assertThat(postResponse).isNotNull()
			.hasFieldOrPropertyWithValue("title", "타이틀수정")
			.hasFieldOrPropertyWithValue("content", "본문수정");
	}

	@DisplayName("게시글 삭제 테스트")
	@Test
	void testRemovePost() {
		// given
		Post post = new Post(2L, "title", "content", USER);
		given(postRepository.findById(anyLong())).willReturn(Optional.of(post));
		given(postConverter.toPostDeleteResponse(anyLong())).willReturn(new PostDeleteResponse(2L));

		// when
		PostDeleteResponse postDeleteResponse = postService.deletePost(2L);

		// then
		verify(postRepository, times(1)).delete(post);
		assertThat(postDeleteResponse.getId()).isEqualTo(2L);
	}
}
