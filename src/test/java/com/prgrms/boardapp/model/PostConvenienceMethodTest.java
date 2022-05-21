package com.prgrms.boardapp.model;

import com.prgrms.boardapp.common.EntityManagerTest;
import com.prgrms.boardapp.repository.PostRepository;
import com.prgrms.boardapp.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.prgrms.boardapp.common.PostCreateUtil.createPost;
import static com.prgrms.boardapp.common.UserCreateUtil.createUser;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

public class PostConvenienceMethodTest extends EntityManagerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("포스팅의 유저정보를 변경할 수 있다.")
    void testChangeUser() {
        User user = createUser();
        User user2 = createUser();
        Post post = createPost();

        execWithTransaction(() -> {
            entityManager.persist(user); // insert
            entityManager.persist(post); // insert
            user.createPost(post); // update
            entityManager.flush();

            entityManager.persist(user2); // insert
            post.changeUser(user2); // update
            entityManager.flush();
        });

        Post findPost = postRepository.findById(post.getId()).get();
        User findUser = userRepository.findById(user.getId()).get();
        User findUser2 = userRepository.findById(user2.getId()).get();

        assertAll(
                () -> assertThat(findPost.getUser()).isEqualTo(findUser2),
                () -> assertThat(findUser2.getPosts().size()).isEqualTo(1),
                () -> assertThat(findUser.getPosts().size()).isZero()
        );
    }
}
