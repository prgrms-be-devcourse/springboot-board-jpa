package com.programmers.board.repository;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.github.javafaker.Faker;
import com.programmers.board.domain.Post;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
class PostRepositoryTest {

	@Autowired
	private PostRepository postRepository;

	private Faker faker = new Faker();

	@Test
	@DisplayName("게시판 저장(얀관관계 x)")
	void testSave() {
		//given
		Post saving = Post.builder()
				.title(faker.leagueOfLegends().champion())
				.content(faker.leagueOfLegends().summonerSpell())
				.build();
		//when
		Post saved = postRepository.save(saving);
		//then
		Assertions.assertNotNull(saved);
		MatcherAssert.assertThat(saved, Matchers.is(saving));
	}

}