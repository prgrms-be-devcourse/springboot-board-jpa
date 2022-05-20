package com.prgrms.board.post.repository;

import com.prgrms.board.post.domain.Post;
import com.prgrms.board.user.domain.User;
import com.prgrms.board.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class PostRepositoryTest {
    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    EntityManagerFactory emf;
    User user;
    Post post1, post2, post3;

    @BeforeEach
    void setUp() {
        user = userRepository.save(
                User.builder()
                        .name("wansu")
                        .age(27L)
                        .hobby("soccer")
                        .build());
        post1 = postRepository.save(
                Post.builder()
                        .title("데브코스")
                        .content("에프팀")
                        .user(user)
                        .build());
        post2 = postRepository.save(
                Post.builder()
                        .title("데브코스")
                        .content("최고")
                        .user(user)
                        .build());
        post3 = postRepository.save(
                Post.builder()
                        .title("스코브데")
                        .content("짱짱")
                        .user(user)
                        .build());
    }

    @AfterEach
    void tearDown() {
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void post_저장() {
        Assertions.assertThat(postRepository.findById(post1.getPostId())).isPresent();
    }

    @Test
    void n1_문제_테스트() {
        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            users.add(
                    User.builder()
                            .name("dev" + i)
                            .age((long) i)
                            .hobby("" + i)
                            .build());
        }
        List<User> saveUsers = userRepository.saveAll(users);
        List<Post> posts = new ArrayList<>();
        for (User saveUser : saveUsers) {
            for (int i = 0; i < 10; i++) {
                posts.add(
                        Post.builder()
                                .title("hello" + i)
                                .content("count" + i)
                                .user(saveUser)
                                .build());
            }
        }
        postRepository.saveAll(posts);
        entityManager.clear();
        List<User> userList = userRepository.findAll();
    }

    @Test
    void user_삭제() {
        postRepository.deleteById(post1.getPostId());
        Assertions.assertThat(postRepository.findById(post1.getPostId())).isNotPresent();
    }

}