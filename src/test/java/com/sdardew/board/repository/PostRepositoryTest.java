package com.sdardew.board.repository;

import com.sdardew.board.domain.post.Post;
import com.sdardew.board.domain.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
    user1 = new User();
    user1.setName("user");
    user1.setHobby("movie");
    user1.setAge(20);
    user1.setCreateAt(LocalDateTime.now());


    user2 = new User();
    user2.setName("user2");
    user2.setHobby("cooking");
    user2.setAge(22);
    user2.setCreateAt(LocalDateTime.now());

    post1 = new Post();
    post1.setUser(user1);
    post1.setTitle("title");
    post1.setContent("content");
    post1.setCreateAt(LocalDateTime.now());
  }

  @Test
  void testInsert() {
    userRepository.save(user1);
    postRepository.save(post1);
    List<Post> all = postRepository.findAll();
    assertThat(all.size()).isEqualTo(1);
  }
}