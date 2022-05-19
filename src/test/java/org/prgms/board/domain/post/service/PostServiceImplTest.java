package org.prgms.board.domain.post.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.BaseTest;
import org.prgms.board.domain.post.domain.Post;
import org.prgms.board.domain.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

class PostServiceImplTest extends BaseTest {

	@Autowired
	PostService postService;

	private User user;

	@BeforeEach
	void setUp() {
		user = User.create("seunghan", 25);
		em.persist(user);
	}

	@Test
	void 게시글_작성_테스트() {
		// when
		final Post savedPost = postService.writePost("title", "this is content", user.getId());
		clearPersistenceContext();

		//then
		final Post post = postService.findPost(savedPost.getId());

		assertThat(post.getTitle()).isEqualTo("title");
		assertThat(post.getContent()).isEqualTo("this is content");
	}

	@Test
	void 게시글_수정_테스트() {
		// given
		final Post savedPost = postService.writePost("title", "this is content.", user.getId());

		//when
		postService.updatePost("updated title", "updated content", savedPost.getId());
		clearPersistenceContext();

		//then
		final Post findPost = postService.findPost(savedPost.getId());

		assertThat(findPost.getTitle()).isEqualTo("updated title");
		assertThat(findPost.getContent()).isEqualTo("updated content");
		assertThat(findPost.getWriter().getId()).isEqualTo(user.getId());
	}

	@Test
	void 게시글_페이징_기능_테스트() {
		// given
		initPostsForTest();

		int page = 0;
		int size = 10;

		final PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));

		// when
		final Page<Post> response = postService.getPostPage(pageRequest);

		// then
		final List<String> titles = IntStream.rangeClosed(1, size)
			.boxed()
			.map(i -> "title" + i).collect(Collectors.toList());

		assertThat(response.getNumberOfElements()).isEqualTo(size);
		assertThat(response.getContent()).extracting("title").containsExactly(titles.toArray());

	}

	/** Paging 테스트를 위한 post 세팅 **/
	private void initPostsForTest() {
		for (int i = 1; i <= 100; ++i) {
			String title = "title" + i;
			String content = "content" + i;

			postService.writePost(title, content, user.getId());
		}
	}
}
