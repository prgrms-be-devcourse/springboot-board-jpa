package com.programmers.kwonjoosung.board.repository;

import com.programmers.kwonjoosung.board.model.Post;
import com.programmers.kwonjoosung.board.model.User;
import com.programmers.kwonjoosung.board.model.dto.PostInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void findByUserIdTest() {
        // given
        User user = new User("테스터", 28, "운동");
        User savedUser = userRepository.save(user);
        Post post = new Post("테스트", "테스트 중");
        post.setUser(savedUser);
        postRepository.save(post);
        // when
        PostInfo postInfo = postRepository.findByUserId(1L).get(0);
        // then
        assertThat(postInfo.userName()).isEqualTo("테스터");
    }

}