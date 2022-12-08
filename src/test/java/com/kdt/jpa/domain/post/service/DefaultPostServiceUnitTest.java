package com.kdt.jpa.domain.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kdt.jpa.domain.member.MemberConverter;
import com.kdt.jpa.domain.member.dto.MemberResponse;
import com.kdt.jpa.domain.member.model.Member;
import com.kdt.jpa.domain.member.service.MemberService;
import com.kdt.jpa.domain.post.PostConverter;
import com.kdt.jpa.domain.post.dto.PostRequest;
import com.kdt.jpa.domain.post.dto.PostResponse;
import com.kdt.jpa.domain.post.model.Post;
import com.kdt.jpa.domain.post.repository.PostRepository;
import com.kdt.jpa.exception.ServiceException;

@ExtendWith(MockitoExtension.class)
public class DefaultPostServiceUnitTest {
	@InjectMocks
	DefaultPostService postService;

	@Mock
	PostRepository postRepository;

	@Mock
	MemberService memberService;

	@Spy
	MemberConverter memberConverter = new MemberConverter();
	@Spy
	PostConverter postConverter = new PostConverter(memberConverter);

	static Member member;

	static MemberResponse memberResponse;

	@BeforeEach
	void setup() {
		member = Member.builder()
			.id(1L)
			.name("jinhyungPark")
			.age(27)
			.hobby("개발")
			.build();
		memberResponse = MemberResponse.builder()
			.id(member.getId())
			.name(member.getName())
			.age(member.getAge())
			.hobby(member.getHobby())
			.build();
	}

	@Test
	@DisplayName("게시글 작성 성공 테스트")
	void writeSuccess() {
		//given
		Post post = Post.builder()
			.id(1L)
			.title("title")
			.content("content")
			.author(member)
			.build();
		given(memberService.findById(any(Long.class))).willReturn(memberResponse);
		given(postRepository.save(any(Post.class))).willReturn(post);
		PostRequest.WritePostRequest writePostRequest = new PostRequest.WritePostRequest("title", "content",
			member.getId());

		//when
		PostResponse.WritePostResponse writePostResponse = postService.write(writePostRequest);

		//then
		verify(memberService, times(1)).findById(any(Long.class));
		verify(postConverter, times(1)).toEntity(writePostRequest, memberResponse);
		verify(postConverter, times(1)).toWritePostResponse(post);

		assertThat(writePostResponse.id()).isNotNull();
	}

	@Test
	@DisplayName("게시글 수정 성공 테스트")
	void updateSuccess() {
		//given
		PostRequest.UpdatePostRequest updatePostRequest = new PostRequest.UpdatePostRequest("updated title",
			"updated content");
		Post post = Post.builder()
			.id(1L)
			.title("title")
			.content("content")
			.author(member)
			.build();
		given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));

		//when
		PostResponse.UpdatePostResponse updatedPost = postService.update(post.getId(), updatePostRequest);

		//then
		verify(postRepository, times(1)).findById(any(Long.class));
		verify(postConverter, times(1)).toUpdatePostResponse(any(Post.class));

		assertThat(updatedPost.title()).isEqualTo(updatePostRequest.title());
		assertThat(updatedPost.content()).isEqualTo(updatePostRequest.content());
	}

	@Test
	@DisplayName("게시글 제목 수정 성공 테스트")
	void updateTitleSuccess() {
		//given
		PostRequest.UpdatePostRequest updatePostRequest = new PostRequest.UpdatePostRequest("updated title",
			null);
		Post post = Post.builder()
			.id(1L)
			.title("title")
			.content("content")
			.author(member)
			.build();
		given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));

		//when
		PostResponse.UpdatePostResponse updatedPost = postService.update(post.getId(), updatePostRequest);

		//then
		verify(postRepository, times(1)).findById(any(Long.class));
		verify(postConverter, times(1)).toUpdatePostResponse(any(Post.class));

		assertThat(updatedPost.title()).isEqualTo(updatePostRequest.title());
		assertThat(updatedPost.content()).isEqualTo(post.getContent());
	}

	@Test
	@DisplayName("게시글 내용 수정 성공 테스트")
	void updateContentSuccess() {
		//given
		PostRequest.UpdatePostRequest updatePostRequest = new PostRequest.UpdatePostRequest(null,
			"updated content");
		Post post = Post.builder()
			.id(1L)
			.title("title")
			.content("content")
			.author(member)
			.build();
		given(postRepository.findById(any(Long.class))).willReturn(Optional.of(post));

		//when
		PostResponse.UpdatePostResponse updatedPost = postService.update(post.getId(), updatePostRequest);

		//then
		verify(postRepository, times(1)).findById(any(Long.class));
		verify(postConverter, times(1)).toUpdatePostResponse(any(Post.class));

		assertThat(updatedPost.title()).isEqualTo(post.getTitle());
		assertThat(updatedPost.content()).isEqualTo(updatePostRequest.content());
	}

	@Test
	@DisplayName("존재하는 게시물 단건 조회 테스트")
	void findByExistId() {
		//given
		Post post = Post.builder()
			.id(1L)
			.title("title")
			.content("content")
			.author(member)
			.build();
		given(postRepository.findById(post.getId())).willReturn(Optional.of(post));
		//when
		PostResponse foundPost = postService.findById(post.getId());

		//then
		verify(postRepository, times(1)).findById(post.getId());
		verify(postConverter, times(1)).toPostResponse(post);

		assertThat(foundPost.id()).isEqualTo(post.getId());
		assertThat(foundPost.title()).isEqualTo(post.getTitle());
		assertThat(foundPost.content()).isEqualTo(post.getContent());
		assertThat(foundPost.author().id()).isEqualTo(member.getId());
		assertThat(foundPost.author().name()).isEqualTo(member.getName());
		assertThat(foundPost.author().age()).isEqualTo(member.getAge());
		assertThat(foundPost.author().hobby()).isEqualTo(member.getHobby());
	}

	@Test
	@DisplayName("존재하지 않는 게시물 단건 조회 테스트")
	void findByNotExistId() {
		//given
		Long notExistPostId = -987654321L;
		given(postRepository.findById(notExistPostId)).willReturn(Optional.empty());

		//when, then
		assertThatThrownBy(() -> postService.findById(notExistPostId)).isInstanceOf(ServiceException.class);

		verify(postRepository, times(1)).findById(any(Long.class));
	}
}
