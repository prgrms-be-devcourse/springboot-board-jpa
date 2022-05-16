package org.prgms.board.domain.post.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.post.domain.Post;
import org.prgms.board.domain.post.dto.PostDto;
import org.prgms.board.domain.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Transactional
class PostServiceImplTest {

	@Autowired
	PostService postService;

	@PersistenceContext
	EntityManager em;
	private User user;

	@BeforeEach
	void setUp() {
		String name = "seunghan";
		int age = 25;

		user = User.create(name, age);

		em.persist(user);
	}

	@DisplayName("게시글 작성 테스트")
	@Test
	void post_write_test() {
		// given
		String title = "title";
		String content = "this is content.";

		//when
		final Post savedPost = postService.writePost(title, content, user.getId());

		//then
		final Post post = postService.findPost(savedPost.getId());

		assertThat(post.getTitle()).isEqualTo(title);
		assertThat(post.getContent()).isEqualTo(content);
		assertThat(post.getWriter().getName()).isEqualTo(user.getName());
		assertThat(post.getWriter().getAge()).isEqualTo(user.getAge());
	}

	@DisplayName("게시글 수정 테스트")
	@Test
	void update_write_test() {
		// given
		final Post savedPost = postService.writePost("title", "this is content.", user.getId());

		//when
		String updateTitle = "updated Title";
		String updateContent = "updated Content";

		postService.updatePost(updateTitle, updateContent, savedPost.getId());
		em.flush();
		em.clear(); // 영속성 컨텍스트 초기화

		//then
		final Post findPost = postService.findPost(savedPost.getId());

		assertThat(findPost.getTitle()).isEqualTo(updateTitle);
		assertThat(findPost.getContent()).isEqualTo(updateContent);
		assertThat(findPost.getWriter().getName()).isEqualTo(user.getName());
		assertThat(findPost.getWriter().getAge()).isEqualTo(user.getAge());
	}

	@DisplayName("게시글 페이징 기능 테스트")
	@Test
	void paging_post_test() {
		// given
		initPosts();

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
		assertThat(response.getContent()).extracting("title").containsExactlyInAnyOrder(titles.toArray());

	}

	/** Paging 테스트를 위한 post 세팅 **/
	private void initPosts() {
		for (int i = 1; i <= 100; ++i) {
			String title = "title" + i;
			String content = "content" + i;

			postService.writePost(title, content, user.getId());
		}
	}
}
