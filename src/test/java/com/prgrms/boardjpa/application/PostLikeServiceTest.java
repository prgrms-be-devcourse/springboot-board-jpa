package com.prgrms.boardjpa.application;

import static org.assertj.core.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.boardjpa.application.post.exception.LikeOwnPostException;
import com.prgrms.boardjpa.application.post.model.Post;
import com.prgrms.boardjpa.application.post.repository.PostLikeRepository;
import com.prgrms.boardjpa.application.post.repository.PostRepository;
import com.prgrms.boardjpa.application.post.service.PostService;
import com.prgrms.boardjpa.application.user.model.Hobby;
import com.prgrms.boardjpa.application.user.model.User;
import com.prgrms.boardjpa.application.user.repository.UserRepository;

@SpringBootTest
@Transactional
public class PostLikeServiceTest {
	@Autowired
	private PostService postService;
	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostLikeRepository postLikeRepository;

	private User writer;

	private User notWriter;

	private Post likedPost1;

	private Post likedPost2;

	private Post notYetLikedPost;

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

		likedPost2 = Post.builder()
			.title("title11")
			.content("content12")
			.writer(writer)
			.build();

		notYetLikedPost = Post.builder()
			.title("title222")
			.content("content222")
			.writer(writer)
			.build();

		userRepository.save(writer);
		userRepository.save(notWriter);
		postRepository.save(likedPost1);
		postRepository.save(likedPost2);
		postRepository.save(notYetLikedPost);

		likedPost1.like(notWriter);
		likedPost2.like(notWriter);

		postLikeRepository.flush();
		postRepository.flush();
		userRepository.flush();
	}

	@AfterEach
	void tearDown() {
		postLikeRepository.deleteAllInBatch();
		postRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("현재 사용자가 좋아요 하지 않았던 게시글에 대해 좋아요 요청을 하면 , 전체 좋아요 수가 1 증가한다")
	public void increaseTotalLikesByOne() {
		// given
		int beforeLikedCount = notYetLikedPost.getLikeCount();

		// when
		postService.toggleLike(notWriter, notYetLikedPost.getId());

		int afterLikedCount = notYetLikedPost.getLikeCount();

		// then
		assertThat(afterLikedCount)
			.isEqualTo((beforeLikedCount + 1));
	}

	@Test
	@DisplayName("현재 사용자가 좋아요 했던 게시글에 대해 좋아요 요청을 하면, 전체 좋아요 수가 1 감소한다")
	public void decreaseTotalLikes() {
		int expectedCount = likedPost1.getLikeCount() > 0 ? likedPost1.getLikeCount() - 1 : 0;

		postService.toggleLike(notWriter, likedPost1.getId());

		Assertions.assertThat(likedPost1.getLikeCount())
			.isEqualTo(expectedCount);
	}

	// @Test
	// @DisplayName("자신이 좋아요 누른 게시글을 불러올 수 있다")
	// public void getAllLikedPosts() {
	// 	postService.toggleLike(notWriter, likedPost1.getId());
	// 	postService.toggleLike(notWriter, likedPost2.getId());
	//
	// 	List<Post> likedPosts = postRepository.findAllLikedByUser(user);
	// 	Assertions.assertThat(likedPosts.size()).isEqualTo(2);
	//
	// }

	@Test
	@DisplayName("자신이 작성한 게시글에 대해 좋아요를 누르면 예외가 발생한다")
	public void likeOwnPost() {
		Assertions.assertThatThrownBy(() -> postService.toggleLike(writer, likedPost1.getId()))
			.isInstanceOf(LikeOwnPostException.class);
	}
}
