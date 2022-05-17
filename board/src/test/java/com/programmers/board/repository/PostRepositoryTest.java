package com.programmers.board.repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.stream.Stream;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.Assert;

import com.github.javafaker.Faker;
import com.programmers.board.config.JpaAuditConfig;
import com.programmers.board.controller.PageRequestDto;
import com.programmers.board.domain.Post;
import com.programmers.board.exception.ServiceException;

@Import(JpaAuditConfig.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	TestEntityManager tem;

	private Faker faker = new Faker();
	private Post saving;

	/**
	 * NOTE :
	 *  insert 쿼리가 안날라간다..
	 *  sol : 쓰기 지연 저장소에 있다가 rollback 할 것을 알고 쿼리 최적화 해주는 것 같다.
	 */
	@Rollback(value = false)
	@Order(10)
	@Test
	@DisplayName("게시판 저장(얀관관계 x)")
	void testSave() {
		//given
		saving = Post.builder()
				.title(faker.leagueOfLegends().champion())
				.content(faker.leagueOfLegends().summonerSpell())
				.build();
		//when
		Post saved = postRepository.save(saving);

		//then
		Assertions.assertNotNull(saved);
		MatcherAssert.assertThat(saved, Matchers.is(saving));
	}

	@Order(20)
	@Test
	@DisplayName("계시판 id로 조회")
	void testFindById() {
		//given

		//when
		Post foundPost = postRepository.findById(saving.getId())
				.orElseThrow(() -> new ServiceException.NotFoundResource("post 가 존재하지 않습니다."));
		//then
		Assertions.assertNotNull(foundPost);
		MatcherAssert.assertThat(foundPost, Matchers.samePropertyValuesAs(saving));
	}

	@Order(30)
	@Rollback(value = false)
	@Test
	@DisplayName("게시판 title, content update")
	void testUpdate() {
		//given
		Post requestUpdate = Post.builder()
				.title(faker.artist().name())
				.content(faker.superhero().name())
				.build();
		//when
		Post foundPost = postRepository.findById(saving.getId()).orElseThrow(RuntimeException::new);
		Post updated = foundPost.update(requestUpdate);
		Post result = postRepository.findById(foundPost.getId()).orElseThrow(RuntimeException::new);
		//then
		org.assertj.core.api.Assertions.assertThat(result.getTitle()).isEqualTo(requestUpdate.getTitle());
		org.assertj.core.api.Assertions.assertThat(result.getContent()).isEqualTo(requestUpdate.getContent());

	}

	@Order(40)
	@Rollback(value = false)
	@Test
	@DisplayName("게시판 id로 soft delete")
	void testSoftDeleteById() {
		//given

		//when
		Post savedPost = postRepository.findById(saving.getId()).orElseThrow(RuntimeException::new);
		savedPost.softDelete();
		Post softDeletedPost = postRepository.findById(saving.getId()).orElseThrow(RuntimeException::new);
		//then

		Assertions.assertNotNull(softDeletedPost);
		Assertions.assertTrue(softDeletedPost.isDeleteYn());

	}

	@Order(50)
	@Test
	@DisplayName("게시판 id로 실제 삭제")
	void testDeleteById() {
		//given

		//when
		postRepository.delete(saving);
		//then

		Assertions.assertThrows(ServiceException.NotFoundResource.class, () ->
						postRepository.findById(saving.getId()).orElseThrow(() -> new ServiceException.NotFoundResource("")),
				"not found resources...");
	}

	@Order(60)
	@Test
	@Rollback(value = false)
	@DisplayName("게시판 전체 조회 [페이징]")
	void testFindAll() {

		//given

		int requestPageNumber = 4;
		int pageSize = 10;

		List<Post> demos = Stream.generate(() -> {
			return Post.builder()
					.title(faker.superhero().name())
					.content(faker.superhero().descriptor())
					.build();
		}).limit(90).toList();
		postRepository.saveAll(demos);

		PageRequest requestPage = PageRequest.of(requestPageNumber - 1, pageSize, Sort.by("createdAt").descending());

		//when
		Page<Post> pages = postRepository.findAll(requestPage);

		//then
		Assertions.assertNotNull(pages);
		org.assertj.core.api.Assertions.assertThat(pages.getContent()).hasSize(10);
		org.assertj.core.api.Assertions.assertThat(pages.getTotalPages()).isEqualTo(9);
		org.assertj.core.api.Assertions.assertThat(pages.getTotalElements()).isEqualTo(90);
		org.assertj.core.api.Assertions.assertThat(pages.getPageable().getPageNumber()).isEqualTo(requestPageNumber - 1);
		org.assertj.core.api.Assertions.assertThat(pages.getPageable().getSort().isSorted()).isEqualTo(true);

	}

}