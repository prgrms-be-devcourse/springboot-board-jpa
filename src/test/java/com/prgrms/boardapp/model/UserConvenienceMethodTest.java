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

public class UserConvenienceMethodTest extends EntityManagerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PostRepository postRepository;

    @Test
    @DisplayName("유저는 게시글을 포스팅할 수 있다.")
    void testCreatePost() {
        User user = createUser();
        Post post = createPost();
        Post post2 = createPost();

        execWithTransaction(() -> {
            entityManager.persist(user);
            entityManager.persist(post);
            entityManager.persist(post2);

            user.createPost(post);
            user.createPost(post2);
        });
        User findUser = userRepository.findById(user.getId()).get();

        assertThat(findUser.getPosts().size()).isEqualTo(2);
    }

    @Test
    @DisplayName("새로운 글이 추가될 때, Post의 Member가 결정된다.")
    void testCreatePostUpdate() {
        User user = createUser();
        Post post = createPost();

        execWithTransaction(() -> {
            entityManager.persist(user);
            entityManager.persist(post);

            user.createPost(post);
        });
        User findUser = userRepository.findById(user.getId()).get();
        Post findPost = postRepository.findById(post.getId()).get();

        assertThat(findPost.getId()).isEqualTo(findUser.getPosts().get(0).getId());
    }
}
