package com.prgrms.boardjpa.application;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.boardjpa.application.post.model.Post;
import com.prgrms.boardjpa.application.post.repository.PostRepository;
import com.prgrms.boardjpa.application.user.model.Hobby;
import com.prgrms.boardjpa.application.user.model.User;
import com.prgrms.boardjpa.application.user.repository.UserRepository;

@Transactional
@SpringBootTest
public class PostLikeServiceTest {

	private PostLikeService postLikeService;

	private PostRepository postRepository;

	private UserRepository userRepository;

	private User writer;

	private User notWriter;

	private Post likedPost1;

	@BeforeEach
	void setUp() {
		writer = User.builder()
				.email("abc@naver")
				.age(44)
				.hobby(Hobby.NONE)
				.password("123")
				.name("abc")
				.build();

		notWriter = User.builder()
				.email("efg@naver")
				.age(22)
				.hobby(Hobby.NONE)
				.password("123")
				.name("efg")
				.build();

		likedPost1 = Post.builder()
				.title("title01")
				.content("content02")
				.writer(writer)
				.build();

		userRepository.save(writer);
		userRepository.save(notWriter);
		postRepository.save(ownPost);
		postRepository.save(likedPost1);

	}


	@Nested
	@DisplayName("현재 사용자가 좋아요 하지 않았던 게시글에 대하여")
	public class UnlikedPostTest {
		private Post notYetLikedPost;

		@BeforeEach
		void setUp() {
			notYetLikedPost = Post.builder()
					.title("title")
					.content("content")
					.writer(writer)
					.build();

			postRepository.save(notYetLikedPost);
		}

		@Nested
		@DisplayName("좋아요 되어있지 않은 게시글에 좋아요 요청을 하면 ")
		public class LikeTest {
			@Test
			@DisplayName("전체 좋아요 수가 1 증가한다")
			public void increaseTotalLikesByOne() {
				// given
				int beforeLikedCount = notYetLikedPost.getLikeCount();

				// when
				postLikeService.toggleLike(notWriter, notYetLikedPost.getId());

				int afterLikedCount = notYetLikedPost.getLikeCount();

				// then
				assertThat(afterLikedCount)
						.isEqualTo((beforeLikedCount + 1));
			}

			@Test
			@DisplayName("좋아요를 활성화 상태로 전환한다")
			public void enabledLiked() {
				postLikeService.toggleLike(notWriter, notYetLikedPost.getId());

				assertThat(notYetLikedPost.isLikedByMe())
						.isTrue();
			}
		}
	}

	@Nested
	@DisplayName("좋아요 체크가 되어있는 게시글에 대하여")
	public class LikedPostTest {
		private Post likedPost;

		@BeforeEach
		void setUp() {
			likedPost = Post.builder()
					.title("title33")
					.content("content33")
					.writer(writer)
					.build();

			postRepository.save(likedPost);
		}

		@Nested
		@DisplayName("좋아요 요청을 하면 ")
		public class DuplicatedLikeTest {
			@Test
			@DisplayName("좋아요 체크 해제 상태로 전환한다")
			public void disableLiked() {
				postLikeService.toggleLike(notWriter, likedPost.getId());

				Assertions.assertThat(likedPost.isLikedByMe())
						.isFalse();
			}

			@Test
			@DisplayName("전체 좋아요 수가 1 감소한다")
			public void decreaseTotalLikes() {
				int beforeCount = likedPost.getLikeCount();

				postLikeService.toggleLike(notWriter, likedPost.getId());

				Assertions.assertThat(likedPost.getLikeCount())
						.isEqualTo((beforeCount - 1));
			}
		}
	}


	@Test
	@DisplayName("자신이 좋아요 누른 게시글을 불러올 수 있다")
	public void getAllLikedPosts() {

	}

	@Test
	@DisplayName("자신이 작성한 게시글에 대해 좋아요를 누르면 예외가 발생한다")
	public void likeOwnPost() {
		Post ownPost = Post.builder()
				.title("title00")
				.content("content00")
				.writer(writer)
				.build();

		postRepository.save(ownPost);

		Assertions.assertThatThrownBy(() -> postLikeService.toggleLike(writer, ownPost.getId()))
				.hasMessage("내가 작성한 글에는 좋아요 할 수 없습니다");

	}
}
