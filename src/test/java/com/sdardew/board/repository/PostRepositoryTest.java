package com.sdardew.board.repository;

import com.sdardew.board.domain.post.Post;
import com.sdardew.board.domain.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PostRepositoryTest {
  @Autowired
  PostRepository postRepository;

  @Autowired
  UserRepository userRepository;

  private static User user1;
  private static User user2;

  private static Post post1;

  @BeforeAll
  static void beforeAll() {
    user1 = new User("user", 20, "movie", LocalDateTime.now());
    user2 = new User("user2", 22, "cook", LocalDateTime.now());
    post1 = new Post("title", "content", LocalDateTime.now(), user1);
  }

  @Test
  void testInsert() {
    userRepository.save(user1);
    postRepository.save(post1);
    Optional<Post> found = postRepository.findById(post1.getId());
    assertAll(
      () -> assertThat(found.isPresent()).isTrue(),
      () -> assertThat(found.get().getTitle()).isEqualTo("title"),
      () -> assertThat(found.get().getContent()).isEqualTo("content"),
      () -> assertThat(found.get().getUser()).isEqualTo(user1)
    );
  }
}