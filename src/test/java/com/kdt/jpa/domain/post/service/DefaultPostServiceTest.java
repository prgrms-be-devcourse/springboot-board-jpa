package com.kdt.jpa.domain.post.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.persistence.EntityManager;
import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.kdt.jpa.domain.member.dto.MemberRequest;
import com.kdt.jpa.domain.member.dto.MemberResponse;
import com.kdt.jpa.domain.member.model.Member;
import com.kdt.jpa.domain.member.service.MemberService;
import com.kdt.jpa.domain.post.dto.PostRequest;
import com.kdt.jpa.domain.post.dto.PostResponse;
import com.kdt.jpa.exception.ServiceException;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DefaultPostServiceTest {

	@Autowired
	PostService postService;

	@Autowired
	MemberService memberService;

	@Autowired
	EntityManager entityManager;

	Member member;

	@BeforeAll
	void setup() {
		MemberRequest.JoinMemberRequest joinMemberRequest = new MemberRequest.JoinMemberRequest("jinhyungPark", 27,
			"개발");

		MemberResponse.JoinMemberResponse join = memberService.join(joinMemberRequest);
		member = new Member(
			join.id(),
			joinMemberRequest.name(),
			joinMemberRequest.age(),
			joinMemberRequest.hobby()
		);
	}

	@Test
	@DisplayName("게시글 작성 성공 테스트")
	void writeSuccess() {
		//given
		PostRequest.WritePostRequest writePostRequest = new PostRequest.WritePostRequest("title", "content",
			member.getId());

		//when
		PostResponse.WritePostResponse writePostResponse = postService.write(writePostRequest);
		PostResponse foundPost = postService.findById(writePostResponse.id());

		//then
		assertThat(writePostResponse.id()).isNotNull();
		assertThat(foundPost.author().id()).isEqualTo(member.getId());
		assertThat(foundPost.title()).isEqualTo(writePostRequest.title());
		assertThat(foundPost.content()).isEqualTo(writePostRequest.content());
	}

	@Test
	@DisplayName("게시글 수정 성공 테스트")
	void updateSuccess() {
		//given
		PostRequest.WritePostRequest writePostRequest = new PostRequest.WritePostRequest("title", "content",
			member.getId());
		PostResponse.WritePostResponse writePostResponse = postService.write(writePostRequest);
		PostRequest.UpdatePostRequest updatePostRequest = new PostRequest.UpdatePostRequest("updated title",
			"updated content");

		//when
		PostResponse.UpdatePostResponse updatedPost = postService.update(writePostResponse.id(), updatePostRequest);
		PostResponse foundPost = postService.findById(writePostResponse.id());

		//then
		assertThat(foundPost.id()).isEqualTo(writePostResponse.id());
		assertThat(foundPost.title()).isEqualTo(updatePostRequest.title());
		assertThat(foundPost.content()).isEqualTo(updatePostRequest.content());

		assertThat(updatedPost.title()).isEqualTo(updatePostRequest.title());
		assertThat(updatedPost.content()).isEqualTo(updatePostRequest.content());
	}

	@Test
	@DisplayName("게시글 제목을 공백으로 수정 시 실패한다.")
	void updateWithBlankTitleFail() {
		//given
		PostRequest.WritePostRequest writePostRequest = new PostRequest.WritePostRequest("title", "content",
			member.getId());
		PostResponse.WritePostResponse writePostResponse = postService.write(writePostRequest);
		PostRequest.UpdatePostRequest updatePostRequest = new PostRequest.UpdatePostRequest(" ", "updated content");

		//when
		postService.update(writePostResponse.id(), updatePostRequest);

		//then
		assertThatThrownBy(() -> entityManager.flush()).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("게시글 내용을 공백으로 수정 시 실패한다.")
	void updateWithBlankContentFail() {
		//given
		PostRequest.WritePostRequest writePostRequest = new PostRequest.WritePostRequest("title", "content",
			member.getId());
		PostResponse.WritePostResponse writePostResponse = postService.write(writePostRequest);
		PostRequest.UpdatePostRequest updatePostRequest = new PostRequest.UpdatePostRequest("updated title", " ");

		//when
		postService.update(writePostResponse.id(), updatePostRequest);

		//then
		assertThatThrownBy(() -> entityManager.flush()).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("게시글 제목 수정 성공 테스트")
	void updateTitleSuccess() {
		//given
		PostRequest.WritePostRequest writePostRequest = new PostRequest.WritePostRequest("title", "content",
			member.getId());
		PostResponse.WritePostResponse writePostResponse = postService.write(writePostRequest);
		PostRequest.UpdatePostRequest updatePostRequest = new PostRequest.UpdatePostRequest("updated title", null);

		//when
		PostResponse.UpdatePostResponse updatedPost = postService.update(writePostResponse.id(), updatePostRequest);
		PostResponse foundPost = postService.findById(writePostResponse.id());

		//then
		assertThat(foundPost.id()).isEqualTo(writePostResponse.id());
		assertThat(foundPost.title()).isEqualTo(updatePostRequest.title());
		assertThat(foundPost.content()).isEqualTo(writePostRequest.content());

		assertThat(updatedPost.title()).isEqualTo(updatePostRequest.title());
		assertThat(updatedPost.content()).isEqualTo(writePostRequest.content());
	}

	@Test
	@DisplayName("게시글 내용 수정 성공 테스트")
	void updateContentSuccess() {
		//given
		PostRequest.WritePostRequest writePostRequest = new PostRequest.WritePostRequest("title", "content",
			member.getId());
		PostResponse.WritePostResponse writePostResponse = postService.write(writePostRequest);
		PostRequest.UpdatePostRequest updatePostRequest = new PostRequest.UpdatePostRequest(null, "updated content");

		//when
		PostResponse.UpdatePostResponse updatedPost = postService.update(writePostResponse.id(), updatePostRequest);
		PostResponse foundPost = postService.findById(writePostResponse.id());

		//then
		assertThat(foundPost.id()).isEqualTo(writePostResponse.id());
		assertThat(foundPost.title()).isEqualTo(writePostRequest.title());
		assertThat(foundPost.content()).isEqualTo(updatePostRequest.content());

		assertThat(updatedPost.title()).isEqualTo(writePostRequest.title());
		assertThat(updatedPost.content()).isEqualTo(updatePostRequest.content());
	}

	@Test
	@DisplayName("존재하는 게시물 단건 조회 테스트")
	void findByExistId() {
		//given
		PostRequest.WritePostRequest writePostRequest = new PostRequest.WritePostRequest("title", "content",
			member.getId());
		PostResponse.WritePostResponse writePostResponse = postService.write(writePostRequest);

		//when
		PostResponse foundPost = postService.findById(writePostResponse.id());

		//then
		assertThat(foundPost.id()).isEqualTo(writePostResponse.id());
		assertThat(foundPost.title()).isEqualTo(writePostRequest.title());
		assertThat(foundPost.content()).isEqualTo(writePostRequest.content());
		assertThat(foundPost.author().id()).isEqualTo(member.getId());
		assertThat(foundPost.author().name()).isEqualTo(member.getName());
		assertThat(foundPost.author().age()).isEqualTo(member.getAge());
		assertThat(foundPost.author().hobby()).isEqualTo(member.getHobby());
	}

	@Test
	@DisplayName("존재하지않는 게시물 단건 조회 테스트")
	void findByNotExistId() {
		//given
		Long notExistPostId = -987654321L;

		//when, then
		assertThatThrownBy(() -> postService.findById(notExistPostId)).isInstanceOf(ServiceException.class);
	}
}