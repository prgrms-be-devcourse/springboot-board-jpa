package com.programmers.epicblues.jpa_board.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.epicblues.jpa_board.entity.Post;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/*
 학습 테스트
 */
@SpringBootTest
class PostRepositoryTest {

  @Autowired
  PostRepository postRepository;

  @Test
  @DisplayName("save된 post는 영속 컨텍스트에 계속 유지되지 않는다.")
  void test() {
    // Given
    var post = Post.builder().content("hello").title("world").build();

    // When
    var maybePersistedPost = postRepository.save(post);

    // Then
    var queriedPost = postRepository.findById(maybePersistedPost.getId()).orElseThrow();
    assertThat(queriedPost).isNotSameAs(maybePersistedPost);
    assertThat(maybePersistedPost).isEqualTo(post);
  }

  @Test
  @DisplayName("@Transactional이 없을 때 save 메서드로 반환된 post를 수정해도 영속성 컨텍스트에 반영되지 않는다.")
  void change_test() {
    // Given
    var post = Post.builder().content("hello").title("world").build();
    var maybePersistedPost = postRepository.save(post);

    // When
    maybePersistedPost.changeContent("babo");

    //Then
    var queriedPost = postRepository.findById(maybePersistedPost.getId()).orElseThrow();
    assertThat(queriedPost).isNotSameAs(maybePersistedPost);
    assertThat(queriedPost.getContent()).isNotEqualTo(maybePersistedPost.getContent());

  }

  @Test
  @Transactional
  @DisplayName("@Transactional이 있을 때 save 메서드로 반환된 post를 수정할 경우 영속성 컨텍스트에도 이 내용이 반영된다.")
  void change_test_with_transactional_annotation() {
    // Given
    var post = Post.builder().content("hello").title("world").build();
    var persistedPost = postRepository.save(post);

    // When
    persistedPost.changeContent("babo");

    //Then
    var queriedPost = postRepository.findById(persistedPost.getId()).orElseThrow();
    assertThat(queriedPost).isSameAs(persistedPost);

  }
}
