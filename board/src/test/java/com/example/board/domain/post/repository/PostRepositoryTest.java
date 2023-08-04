package com.example.board.domain.post.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.board.domain.post.Post;
import com.example.board.domain.user.User;
import com.example.board.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PostRepositoryTest {

  @Autowired
  PostRepository postRepository;
  @Autowired
  UserRepository userRepository;

  @Autowired
  EntityManager em;

  User givenUser;
  Post givenPost;

  @BeforeEach
  void setUp() {
    givenUser = new User("name", 20, "hobby");
    givenPost = new Post("title", "content", givenUser);
  }

  @Test
  void findById() {

    // given
    userRepository.save(givenUser);
    Post savedPost = postRepository.save(givenPost);
    em.flush();
    em.clear();

    //when
    Post foundPost = postRepository.findById(givenPost.getId()).get();
    em.flush();

    // then
    assertThat(foundPost).isNotNull();
    assertThat(savedPost).usingRecursiveComparison().isEqualTo(foundPost);
  }
}