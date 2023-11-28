package com.programmers.springboard.entity;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

import org.junit.jupiter.api.Test;

class PostTest {

	@Test
	void 포스트_인스턴스를_생성합니다() {
		Member member = new Member();
		Post post = Post.builder()
			.title("Test Title")
			.content("Test Content")
			.member(member)
			.build();

		assertThat(post.getTitle(), is("Test Title"));
		assertThat(post.getContent(), is("Test Content"));
	}

	@Test
	void 포스트를_수정합니다() {
		Member member = new Member();
		Post post = new Post(1L, "Original Title", "Original Content", false, member);

		post.changePostTitleAndContent("Updated Title", "Updated Content");

		assertThat(post.getTitle(), is("Updated Title"));
		assertThat(post.getContent(), is("Updated Content"));
	}

}
