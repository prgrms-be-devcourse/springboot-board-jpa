package com.programmers.springboard.service;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.programmers.springboard.member.domain.Member;
import com.programmers.springboard.post.domain.Post;
import com.programmers.springboard.member.repository.MemberRepository;
import com.programmers.springboard.post.repository.PostRepository;
import com.programmers.springboard.post.dto.PostCreateRequest;
import com.programmers.springboard.post.dto.PostSearchRequest;
import com.programmers.springboard.post.dto.PostUpdateRequest;
import com.programmers.springboard.post.dto.PostResponse;
import com.programmers.springboard.post.service.PostService;

@SpringBootTest
class PostServiceTest {

	private static Member member;
	private static Post post;
	@Autowired
	private PostService postService;
	@Autowired
	private PostRepository postRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		String encodedPassword = passwordEncoder.encode("test1234");
		Member memberToSave = new Member("testuser", "testid", encodedPassword);
		member = memberRepository.save(memberToSave);
		Post postToSave = new Post("testtitle", "testcontent", member);
		post = postRepository.save(postToSave);
	}

	@AfterEach
	void cleanUp() {
		memberRepository.deleteAll();
		postRepository.deleteAll();
	}

	@Test
	void 게시글_생성_성공() {
		// Given
		PostCreateRequest request = new PostCreateRequest("Test Title", "Test Content", member.getId());

		// When
		PostResponse createdPost = postService.createPost(request);

		// Then
		assertThat(createdPost.title(), is(request.title()));
		assertThat(createdPost.content(), is(request.content()));
		postRepository.deleteById(createdPost.postId());
	}

	@Test
	void 게시글_조회_성공() {
		// Given

		// When
		PostResponse foundPost = postService.getPostById(post.getId());

		// Then
		assertThat(foundPost.title(), is(post.getTitle()));
		assertThat(foundPost.content(), is(post.getContent()));
	}

	@Test
	void 게시글_수정_성공() {
		// Given
		PostUpdateRequest updateRequest = new PostUpdateRequest("Updated Title", "Updated Content");

		// When
		PostResponse updatedPost = postService.updatePost(post.getId(), updateRequest);

		// Then
		assertThat(updatedPost.title(), is(updateRequest.title()));
		assertThat(updatedPost.content(), is(updateRequest.content()));
	}

	@Test
	void 게시글_페이징_조회_성공() {
		// Given
		createAndSaveTestPosts();
		PageRequest pageRequest = PageRequest.of(0, 10);
		PostSearchRequest searchRequest = new PostSearchRequest(null);

		// When
		Page<PostResponse> result = postService.getPosts(searchRequest, pageRequest);

		// Then
		assertThat(result.getSize(), is(pageRequest.getPageSize()));
		assertThat(result.getNumber(), is(pageRequest.getPageNumber()));
		postRepository.deleteAll();
	}

	private void createAndSaveTestPosts() {
		for (int i = 0; i < 15; i++) {
			PostCreateRequest request = new PostCreateRequest("Title " + i, "Content " + i, member.getId());
			postService.createPost(request);
		}
	}

	@Test
	void 게시글_삭제_성공() {
		// Given

		// When
		postService.deletePosts(List.of(post.getId()));

		// Then
		Optional<Post> deletedPost = postRepository.findById(post.getId());
		assertThat(deletedPost.isPresent(), is(false));
	}

}
