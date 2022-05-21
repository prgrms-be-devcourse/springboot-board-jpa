package org.programmers.springbootboardjpa.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.springbootboardjpa.domain.Post;
import org.programmers.springbootboardjpa.domain.user.Age;
import org.programmers.springbootboardjpa.domain.user.Name;
import org.programmers.springbootboardjpa.domain.user.Password;
import org.programmers.springbootboardjpa.domain.user.User;
import org.programmers.springbootboardjpa.repository.user.PostRepository;
import org.programmers.springbootboardjpa.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback(value = false)
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;
    @Autowired
    UserRepository userRepository;

    static User testUser;

    @BeforeEach
    void setTestEntity() {
        testUser = new User("KinSlayer", new Password("Shakespeare"), new Name("Hamlet", "Prince"), new Age(LocalDate.of(1120, 5, 28)));
        userRepository.save(testUser);
    }

    @Test
    @DisplayName("Post, User 연관관계 세팅")
    void saveRelation() {
        User user = userRepository.findById(testUser.getUserId()).get();
        Post testPost = new Post("What I am reading is", "Words, words, words...", user);
        postRepository.save(testPost);

        Post foundPost = postRepository.findById(testPost.getPostId()).get();

        assertThat(foundPost.getUser()).isEqualTo(testUser);
    }
}