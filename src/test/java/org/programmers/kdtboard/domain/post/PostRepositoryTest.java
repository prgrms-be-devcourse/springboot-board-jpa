package org.programmers.kdtboard.domain.post;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import org.junit.jupiter.api.Test;
import org.programmers.kdtboard.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@DataJpaTest
class PostRepositoryTest {

	@Autowired
	PostRepository postRepository;

	Post post = Post.create("테스트를 잘 하는 법", "알려드렸습니다.");
	User user = User.create("hyebpark", 12, "잠자기");

	@Test
	void save() {
		//given
		post.setUser(user);
		//when
		var savedPost = postRepository.save(post);
		//then
		assertThat(savedPost).isEqualTo(post);
	}

	@Test
	void findById() {
		//given
		post.setUser(user);
		//when
		var savedPost = postRepository.save(post);
		var foundPost = postRepository.findById(post.getId());
		//then
		assertThat(foundPost).isNotEmpty();
		assertThat(foundPost.get()).isEqualTo(savedPost);

	}

	@Test
	void findAll() {
		//given
		post.setUser(user);
		//when
		var savedPost = postRepository.save(post);
		var posts = postRepository.findAll();
		//then
		assertThat(posts).contains(savedPost);
	}

	@Rollback(value = false)
	@Test
	void update() {
		//given
		var content = "수정한 내용입니다.";
		post.setUser(user);
		var savedPost = postRepository.save(post);
		//when
		savedPost.changeContent(content);
		var foundPost = postRepository.findById(post.getId());
		//then
		log.info("found Post content : {}", content);
		assertThat(foundPost).isNotEmpty();
		assertThat(foundPost.get().getContent()).isEqualTo(content);
	}

	@Test
	void deleteById() {
		//given
		post.setUser(user);
		//when
		postRepository.save(post);
		postRepository.deleteById(post.getId());
		//then
		var foundPost = postRepository.findById(post.getId());

		assertThat(foundPost).isEmpty();
	}
}