package com.prgrms.boardjpa.application.post.repository;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.prgrms.boardjpa.application.post.model.Post;
import com.prgrms.boardjpa.application.post.service.PostService;
import com.prgrms.boardjpa.application.user.model.Hobby;
import com.prgrms.boardjpa.application.user.model.User;
import com.prgrms.boardjpa.application.user.repository.UserRepository;

@SpringBootTest
@Transactional
class PostRepositoryTest {
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

	private Post notYetLikedPost1;

	private Post notYetLikedPost2;

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

		notYetLikedPost1 = Post.builder()
			.title("title222")
			.content("content222")
			.writer(writer)
			.build();

		notYetLikedPost2 = Post.builder()
			.title("title333")
			.content("content333")
			.writer(writer)
			.build();

		userRepository.save(writer);
		userRepository.save(notWriter);
		postRepository.save(likedPost1);
		postRepository.save(likedPost2);
		postRepository.save(notYetLikedPost1);
		postRepository.save(notYetLikedPost2);

		likedPost1.like(notWriter);
		likedPost2.like(notWriter);

		postRepository.flush();
		userRepository.flush();
		postLikeRepository.flush();
	}

	@AfterEach
	void tearDown() {
		postLikeRepository.deleteAllInBatch();
		postRepository.deleteAllInBatch();
		userRepository.deleteAllInBatch();
	}

	@Test
	@DisplayName("특정한 사용자가 좋아요 한 모든 게시글을 찾아올 수 있다")
	public void test() {
		int likedPostsCount = postRepository.findAllLikedBy(notWriter).size();

		Assertions.assertThat(likedPostsCount)
			.isEqualTo(2);
	}
}