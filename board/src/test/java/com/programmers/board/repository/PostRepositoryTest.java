package com.programmers.board.repository;

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
import org.springframework.test.annotation.Rollback;

import com.github.javafaker.Faker;
import com.programmers.board.config.JpaAuditConfig;
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
	 *  쓰기 지연 저장소에 있다가 rollback 할 것을 알고 쿼리 최적화 해주는 것 같다.
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

	@Order(40)
	@Test
	@DisplayName("게시판 id로 삭제")
	void testDeleteById() {
		//given

		//when
		postRepository.delete(saving);
		//then

		Assertions.assertThrows(ServiceException.NotFoundResource.class, () ->
						postRepository.findById(saving.getId()).orElseThrow(() -> new ServiceException.NotFoundResource("")),
				"not found resources...");
	}

}