package org.programmers.kdtboard.domain.post;

import static org.assertj.core.api.AssertionsForInterfaceTypes.*;

import org.junit.jupiter.api.Test;
import org.programmers.kdtboard.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
class PostRepositoryTest {

	@Autowired
	PostRepository postRepository;

	Post post = Post.builder()
		.title("테스트를 잘 하는 법")
		.content("알려드렸습니다.")
		.build();
	User user = User.builder()
		.name("hyebpark")
		.age(12)
		.hobby("잠자기")
		.build();

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
		//then
		var foundPost = postRepository.findById(post.getId());
		assertThat(foundPost).isPresent();
		assertThat(foundPost.get()).isEqualTo(savedPost);

	}

	@Test
	void findAll() {
		//given
		post.setUser(user);
		//when
		var savedPost = postRepository.save(post);
		//then
		var posts = postRepository.findAll();
		assertThat(posts).contains(savedPost);
	}

	@Rollback(value = false)
	@Test
	void update() {
		//given
		var title = "수정한 제목입니다.";
		var content = "수정한 내용입니다.";
		post.setUser(user);
		var savedPost = postRepository.save(post);
		//when
		savedPost.update(title, content);
		//then
		var foundPost = postRepository.findById(post.getId());
		assertThat(foundPost).isPresent();
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
