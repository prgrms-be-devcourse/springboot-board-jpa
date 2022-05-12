package org.prgms.board.domain.post.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.user.domain.User;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostTest {

	@DisplayName("Post 생성 성공 테스트")
	@Test
	void create_post_success() {
		// given
		String title = "first title";
		String content = "this is content";

		// when
		final Post post = Post.create(title, content);

		// then
		assertThat(post.getTitle()).isEqualTo(title);
		assertThat(post.getContent()).isEqualTo(content);
	}

	@DisplayName("Post 생성 실패 테스트")
	@Test
	void create_post_fail() {
		// title test
		assertThrows(IllegalArgumentException.class, () -> Post.create(null, "this is content"));
		assertThrows(IllegalArgumentException.class, () -> Post.create("", "this is content"));

		// content test
		assertThrows(IllegalArgumentException.class, () -> Post.create("title", null));
		assertThrows(IllegalArgumentException.class, () -> Post.create("title", ""));
	}

	@DisplayName("Post update 성공 테스트")
	@Test
	void update_post_success() {
		// given
		String title = "first title";
		String content = "this is content";

		final Post post = Post.create(title, content);

		// when
		String updateTitle = "updated title";
		String updateContent = "updated content";

		post.updatePost(updateTitle, updateContent);

		//then
		assertThat(post.getTitle()).isEqualTo(updateTitle);
		assertThat(post.getContent()).isEqualTo(updateContent);
	}

	@DisplayName("Post update 실패 테스트")
	@Test
	void update_post_fail() {
		// given
		String title = "first title";
		String content = "this is content";

		final Post post = Post.create(title, content);

		// title test
		assertThrows(IllegalArgumentException.class, () -> Post.create(null, "this is content"));
		assertThrows(IllegalArgumentException.class, () -> Post.create("", "this is content"));

		// content test
		assertThrows(IllegalArgumentException.class, () -> Post.create("title", null));
		assertThrows(IllegalArgumentException.class, () -> Post.create("title", ""));
	}

	@DisplayName("setPost 테스트")
	@Test
	void setPost_test() {
		// given
		String title = "first title";
		String content = "this is content";

		final Post post = Post.create(title, content);

		// when
		String name = "seung han";
		int age = 25;

		final User user = User.create(name, age);
		post.setUser(user);

		// then
		final User savedUser = post.getUser();

		assertThat(savedUser.getId()).isEqualTo(user.getId());
		assertThat(savedUser.getName()).isEqualTo(name);
		assertThat(savedUser.getAge()).isEqualTo(age);

	}

}
