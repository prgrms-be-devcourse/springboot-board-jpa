package com.prgrms.boardjpa.post;

import java.time.LocalDateTime;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.prgrms.boardjpa.user.domain.Hobby;
import com.prgrms.boardjpa.user.domain.User;
import com.prgrms.boardjpa.user.UserRepository;

@DataJpaTest
@EnableJpaAuditing
@DisplayName("BoardRepository 테스트")
public class BoardRepositoryTest {

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserRepository userRepository;

	private User writer;

	@BeforeEach
	void setUp() {
		writer = createUser();

		userRepository.save(writer);
	}

	@Nested
	@DisplayName("findAllBy() 메소드에서 ")
	public class PageTest {

		@BeforeEach
		void setUp() {
			Post post1 = postRepository.save(createPost("title", writer, "content"));
			Post post2 = postRepository.save(createPost("title", writer, "content"));
		}

		@ParameterizedTest
		@ValueSource(ints = {1, 2})
		@DisplayName(" page x size <= 전체 게시글 수 라면 찾아온 게시글의 수는 size 와 같다")
		public void testPag(int size) {
			int page = 0;
			Pageable pageable = PageRequest.of(page, size);

			long allPostsCount = postRepository.count();
			long fetchedPostsCount = postRepository.findAllBy(pageable).size();

			Condition<Long> smallerThanTotalPosts = new Condition<>(
				s -> s <= allPostsCount,
				"page * size 는 전체 게시글 수보다 작거나 같아야 합니다"
			);

			Assertions.assertThat((long)page * size).is(smallerThanTotalPosts);
			Assertions.assertThat(fetchedPostsCount).isEqualTo(size);
		}
	}

	@Nested
	@DisplayName("save() 메소드에서")
	public class JpaAuditingTest {
		private Post newlyPublishedPost;

		@BeforeEach
		void setUp() {
			newlyPublishedPost = postRepository.save(createPost("title", writer, "content"));
		}

		@Test
		@DisplayName("저장된 게시글의 createdAt 은 null 이 아니다")
		public void test_creationOfCreatedAt() {
			Post foundPost = postRepository.findById(newlyPublishedPost.getId()).get();

			Assertions.assertThat(foundPost.getCreatedAt()).isNotNull();
		}

		@Test
		@DisplayName("게시글 수정 후 저장하면 수정 전 엔티티의 updatedAt 과 다른 updatedAt 인스턴스를 갖는다")
		public void test_saveUpdatedAtAttribute() {
			LocalDateTime preUpdatedTime = newlyPublishedPost.getUpdatedAt();

			Assertions.assertThat(newlyPublishedPost.getUpdatedAt())
				.isEqualTo(newlyPublishedPost.getCreatedAt());

			newlyPublishedPost.edit("hello", "everybody");

			Post updatedPost = postRepository.saveAndFlush(newlyPublishedPost);

			Assertions.assertThat(preUpdatedTime == updatedPost.getUpdatedAt()).isFalse();
		}

		@Test
		@DisplayName("게시글을 수정하지 않고 저장하면, updatedAt 이 업데이트 되지 않는다")
		public void test_notSaveUpdatedAtAttribute() {
			Assertions.assertThat(newlyPublishedPost.getUpdatedAt()).isEqualTo(newlyPublishedPost.getCreatedAt());

			Post savedPost = postRepository.saveAndFlush(newlyPublishedPost);

			Assertions.assertThat(savedPost.getUpdatedAt()).isEqualTo(newlyPublishedPost.getUpdatedAt());
		}
	}

	private Post createPost(String title, User writer, String content) {
		return Post.builder()
			.title(title)
			.content(content)
			.writer(writer)
			.build();
	}

	private User createUser() {
		return User.builder()
			.email("abc@naver.com")
			.age(27)
			.hobby(Hobby.MOVIE)
			.password("123")
			.name("lee")
			.build();
	}
}
