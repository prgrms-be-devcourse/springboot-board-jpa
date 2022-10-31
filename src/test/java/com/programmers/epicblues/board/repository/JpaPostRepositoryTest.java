package com.programmers.epicblues.board.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.epicblues.board.entity.BaseEntity;
import com.programmers.epicblues.board.entity.Post;
import com.programmers.epicblues.board.entity.User;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import util.EntityFixture;

/*
 학습 테스트
 */
@SpringBootTest
class JpaPostRepositoryTest {

  @Autowired
  JpaPostRepository jpaPostRepository;

  @PersistenceContext
  EntityManager entityManager;

  @Test
  @DisplayName("@Transactional이 없을 때 save된 post는 영속 컨텍스트에 계속 유지되지 않는다.")
  void saved_post_detach_from_context_when_transaction_is_not_annotated() {
    // Given
    Post post = Post.builder().content("hello").title("world").build();

    // When
    Post maybePersistedPost = jpaPostRepository.save(post);

    // Then
    Post queriedPost = jpaPostRepository.findById(maybePersistedPost.getId()).orElseThrow();
    assertThat(queriedPost).isNotSameAs(maybePersistedPost);
    assertThat(maybePersistedPost).isEqualTo(post);

    // teardown
    jpaPostRepository.delete(queriedPost);
  }

  @Test
  @DisplayName("@Transactional이 없을 때 save 메서드로 반환된 post를 수정해도 영속성 컨텍스트에 반영되지 않는다.")
  void change_test() {
    // Given
    Post post = Post.builder().content("hello").title("world").build();
    Post maybePersistedPost = jpaPostRepository.save(post);

    // When
    maybePersistedPost.updateContent("babo");

    //Then
    Post queriedPost = jpaPostRepository.findById(maybePersistedPost.getId()).orElseThrow();
    assertThat(queriedPost).isNotSameAs(maybePersistedPost);
    assertThat(queriedPost.getContent()).isNotEqualTo(maybePersistedPost.getContent());

  }

  @Test
  @Transactional
  @DisplayName("@Transactional이 있을 때 save 메서드로 반환된 post를 수정할 경우 영속성 컨텍스트에도 이 내용이 반영된다.")
  void change_test_with_transactional_annotation() {
    // Given
    Post post = Post.builder().content("hello").title("world").build();
    Post persistedPost = jpaPostRepository.save(post);

    // When
    persistedPost.updateContent("babo");

    //Then
    Post queriedPost = jpaPostRepository.findById(persistedPost.getId()).orElseThrow();
    assertThat(queriedPost).isSameAs(persistedPost);
  }

  @Test
  @Transactional
  @DisplayName("원하는 크기의 Post 페이지를 가져올 수 있어야 한다.")
  void find_all_with_pageable_test() {

    // Given
    User user = EntityFixture.getUser();
    List<Post> posts = EntityFixture.getPostList();
    user.addPosts(posts);
    entityManager.persist(user);
    entityManager.flush();

    // When
    List<Post> queriedPosts = jpaPostRepository.findAll(PageRequest.of(1, 2)).getContent();

    // Then
    assertThat(queriedPosts).containsAnyElementsOf(posts).hasSize(2);
  }

  @Test
  @Transactional
  @DisplayName("생성 시간을 기준으로 내림차순으로 정렬된 post들을 페이지로 가져올 수 있어야 한다.")
  void find_all_with_pageable_test_with_created_at_sorted() {

    // Given
    User user = EntityFixture.getUser();
    // post는 정렬되어 있다.
    List<Post> posts = EntityFixture.getPostList();
    user.addPosts(posts);
    entityManager.persist(user);
    entityManager.flush();

    // When
    List<Post> queriedPosts = jpaPostRepository.findAll(
            PageRequest.of(0, 4, Sort.by("createdAt").descending()))
        .getContent();

    // Then
    assertThat(queriedPosts).containsExactlyElementsOf(
        queriedPosts.stream().sorted(
                Comparator.comparing(BaseEntity::getCreatedAt, Comparator.reverseOrder()))
            .limit(4)
            .collect(Collectors.toList()));
  }

  @Test
  @Transactional
  @DisplayName("repository에 등록되어 있는 post를 id를 통해 가져올 수 있다.")
  void findById() {

    // Given
    List<Post> posts = EntityFixture.getPostList();
    Post targetPost = posts.get(1);
    jpaPostRepository.saveAll(posts);

    // When
    Optional<Post> persistedPost = jpaPostRepository.findById(targetPost.getId());

    // Then
    assertThat(persistedPost).isNotEmpty().contains(targetPost);
  }

  @Test
  @Transactional
  @DisplayName("Post가 save되면 Post의 createdAt 필드가 할당된다.")
  void test_save_assign_required_data_from_database() {
    //given
    User user = EntityFixture.getUser();
    Post post = EntityFixture.getFirstPost();
    entityManager.persist(user);
    entityManager.flush();

    //when
    post.assignUser(user);
    assertThat(post.getCreatedAt()).isNull();
    jpaPostRepository.save(post);

    //then
    assertThat(post.getCreatedAt()).isNotNull();
  }

}
