package com.kdt.jpa.domain.post.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import javax.validation.ConstraintViolationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.kdt.jpa.domain.member.model.Member;
import com.kdt.jpa.domain.member.repository.MemberRepository;
import com.kdt.jpa.domain.member.repository.MemberRepositoryTest;
import com.kdt.jpa.domain.post.model.Post;

@DataJpaTest
@EnableJpaRepositories(basePackageClasses = {PostRepository.class, MemberRepositoryTest.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostRepositoryTest {

	@Autowired
	TestEntityManager entityManager;

	@Autowired
	PostRepository postRepository;

	@Autowired
	MemberRepository memberRepository;

	Member member;

	@BeforeAll
	void setup() {
		member = memberRepository.save(Member.generateNewMemberInstance("jinhyungPark", 27, "개발"));
	}

	@Test
	@DisplayName("게시글 작성 시 Null인 제목은 실패한다.")
	void writeWithNullTitleFail() {
		//given
		Post post = Post.createNew(null, "content", member);

		//when, then
		postRepository.save(post);
		assertThatThrownBy(() -> entityManager.flush()).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("게시글 작성 시 Null인 내용은 실패한다.")
	void writeWithNullContentFail() {
		//given
		Post post = Post.createNew("title", null, member);

		//when, then
		postRepository.save(post);
		assertThatThrownBy(() -> entityManager.flush()).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("게시글 작성 시 Blank인 제목은 실패한다.")
	void writeWithBlankTitleFail() {
		//given
		Post post = Post.createNew("  ", null, member);

		//when, then
		postRepository.save(post);
		assertThatThrownBy(() -> entityManager.flush()).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("게시글 작성 시 Blank인 내용은 실패한다.")
	void writeWithBlankContentFail() {
		//given
		Post post = Post.createNew("title", "   ", member);

		//when, then
		postRepository.save(post);
		assertThatThrownBy(() -> entityManager.flush()).isInstanceOf(ConstraintViolationException.class);
	}

	@Test
	@DisplayName("게시글 작성 시 한 글자 이상 제목은 성공한다.")
	void writeWithTwoLetterTitleSuccess() {
		//given
		Post post = Post.createNew("a", "content", member);

		//when
		Post writtenPost = postRepository.save(post);
		entityManager.flush();

		//then
		Assertions.assertThat(writtenPost.getTitle()).isEqualTo(post.getTitle());
		Assertions.assertThat(writtenPost.getContent()).isEqualTo(post.getContent());
	}

	@Test
	@DisplayName("게시글 작성 시 한 글자 이상 내용은 성공한다.")
	void writeWithTwoLetterContentSuccess() {
		//given
		Post post = Post.createNew("title", "a", member);

		//when
		Post writtenPost = postRepository.save(post);
		entityManager.flush();

		//then
		Assertions.assertThat(writtenPost.getTitle()).isEqualTo(post.getTitle());
		Assertions.assertThat(writtenPost.getContent()).isEqualTo(post.getContent());
	}

	@Test
	@DisplayName("게시글 작성 성공 테스트")
	void writeSuccess() {
		//given
		Post post = Post.createNew("title", "content", member);

		//when
		Post writtenPost = postRepository.save(post);
		entityManager.flush();

		//then
		Assertions.assertThat(writtenPost.getTitle()).isEqualTo(post.getTitle());
		Assertions.assertThat(writtenPost.getContent()).isEqualTo(post.getContent());
	}

}
