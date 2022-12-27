package com.prgrms.devcourse.springjpaboard.domain.post.repository;

import static com.prgrms.devcourse.springjpaboard.domain.user.TestUserObjectProvider.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;

import com.prgrms.devcourse.springjpaboard.domain.post.Post;
import com.prgrms.devcourse.springjpaboard.domain.post.TestPostObjectProvider;
import com.prgrms.devcourse.springjpaboard.domain.user.User;
import com.prgrms.devcourse.springjpaboard.domain.user.repository.UserRepository;

@DataJpaTest
class PostRepositoryTest {

	@Autowired
	PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	@DisplayName("커서기반 페이지네이션으로 조회한 post 개수가 조회하고자 했던 post 개수와 같다")
	void findByIdLessThanTest() {

		//given
		Long cursorId = 15L;
		Integer size = 5;

		User user = createUser();
		userRepository.save(user);
		postRepository.saveAll(TestPostObjectProvider.createPostList(user));

		Pageable pageable = PageRequest.of(0, size, Sort.by("id").descending());

		//when
		Slice<Post> postSlice = postRepository.findByIdLessThan(cursorId, pageable);

		//then
		assertThat(postSlice.getSize()).isEqualTo(size);
	}
}