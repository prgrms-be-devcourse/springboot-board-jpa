package org.prgms.board.domain.post.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.user.domain.User;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostTest {

	User user;

	@BeforeEach
	void setUp() {
		user = User.create("seunghan", 25);
	}

	@DisplayName("Post 생성 성공 테스트")
	@Test
	void create_post_success() {
		// given
		String title = "first title";
		String content = "this is content";

		// when
		final Post post = Post.create(title, content, user);

		// then
		assertThat(post.getTitle()).isEqualTo(title);
		assertThat(post.getContent()).isEqualTo(content);
		assertThat(post.getWriter().getName()).isEqualTo("seunghan");
		assertThat(post.getWriter().getAge()).isEqualTo(25);
	}

	@DisplayName("title이 1자 이상 255자 이하가 아닐 때 Post 생성 실패 테스트")
	@Test
	void create_post_fail_with_wrong_title() {
		assertThrows(IllegalArgumentException.class, () -> Post.create(null, "this is content", user));
		assertThrows(IllegalArgumentException.class, () -> Post.create("", "this is content", user));
	}

	@DisplayName("content가 비어있을 때 Post 생성 실패 테스트")
	@Test
	void create_post_fail_without_content() {
		assertThrows(IllegalArgumentException.class, () -> Post.create("title", null, user));
		assertThrows(IllegalArgumentException.class, () -> Post.create("title", "", user));
	}

	@DisplayName("작성자가 없을 때 Post 생성 실패 테스트")
	@Test
	void create_post_fail_without_user() {
		assertThrows(NullPointerException.class, () -> Post.create("title", "this is content", null));
	}

	@DisplayName("Post update 성공 테스트")
	@Test
	void update_post_success() {
		// given
		String title = "first title";
		String content = "this is content";

		final Post post = Post.create(title, content, user);

		// when
		String updateTitle = "updated title";
		String updateContent = "updated content";

		post.updatePost(updateTitle, updateContent);

		//then
		assertThat(post.getTitle()).isEqualTo(updateTitle);
		assertThat(post.getContent()).isEqualTo(updateContent);
	}

	@DisplayName("title이 1자 이상 255자 이하가 아닐 때 Post update 실패 테스트")
	@Test
	void update_post_fail_with_invalid_title() {
		String title = "first title";
		String content = "this is content";

		final Post post = Post.create(title, content, user);

		assertThrows(IllegalArgumentException.class, () -> post.updatePost(null, "this is content"));
		assertThrows(IllegalArgumentException.class, () -> post.updatePost("", "this is content"));
	}

	@DisplayName("content가 비어있을 때 Post update 실패 테스트")
	@Test
	void update_post_fail_with_invalid_content() {
		String title = "first title";
		String content = "this is content";

		final Post post = Post.create(title, content, user);

		assertThrows(IllegalArgumentException.class, () -> post.updatePost("title", ""));
		assertThrows(IllegalArgumentException.class, () -> post.updatePost("title", null));
	}

}
