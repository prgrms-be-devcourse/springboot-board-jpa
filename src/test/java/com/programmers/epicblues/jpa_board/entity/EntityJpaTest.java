package com.programmers.epicblues.jpa_board.entity;

import static org.assertj.core.api.Assertions.assertThat;

import com.programmers.epicblues.jpa_board.EntityFixture;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

/*
  학습 테스트
 */

@SpringBootTest
@Transactional
class EntityJpaTest {

  private final User USER_FIXTURE = EntityFixture.getUser();
  private final Post POST_FIXTURE_2 = EntityFixture.getSecondPost();
  private final Post POST_FIXTURE_1 = EntityFixture.getFirstPost();
  @Autowired
  EntityManagerFactory entityManagerFactory;
  EntityManager entityManager;

  @BeforeEach
  void setup() {
    entityManager = entityManagerFactory.createEntityManager();
  }

  @AfterEach
  void tearDown() {
    entityManager.close();
  }

  @Test
  @DisplayName("User를 영속 컨텍스트에 보관하면 id를 가져와야 한다.")
  void user_can_be_stored_in_persistence_context() {

    // Given
    assertThat(USER_FIXTURE.getId()).isNull();

    // When
    entityManager.getTransaction().begin();
    entityManager.persist(USER_FIXTURE);
    entityManager.getTransaction().commit();

    // Then
    assertThat(USER_FIXTURE.getId()).isNotNull();
    assertThat(USER_FIXTURE.getCreatedAt()).isNotNull();
  }

  @Test
  @DisplayName("Post를 영속 컨텍스트에 보관하면 id를 가져와야 한다.")
  void post_can_be_stored_in_persistence_context() {

    // Given
    assertThat(POST_FIXTURE_1.getId()).isNull();
    // When
    entityManager.getTransaction().begin();
    entityManager.persist(POST_FIXTURE_1);
    entityManager.getTransaction().commit();
    // Then
    assertThat(POST_FIXTURE_1.getId()).isNotNull();
    assertThat(POST_FIXTURE_1.getCreatedAt()).isNotNull();
  }

  @Test
  @DisplayName("게시글들과 참조로 연결되어 있는 회원을 영속화하면 게시글들도 영속화된다.")
  void relationship_test() {

    // Given
    USER_FIXTURE.addPost(POST_FIXTURE_1);
    USER_FIXTURE.addPost(POST_FIXTURE_2);

    // When
    entityManager.getTransaction().begin();
    entityManager.persist(USER_FIXTURE);
    entityManager.getTransaction().commit();

    // Then
    assertThat(entityManager.contains(POST_FIXTURE_1)).isTrue();
    assertThat(entityManager.contains(POST_FIXTURE_2)).isTrue();

  }

  @Test
  @DisplayName("User가 자신과 연관되어 있는 Post를 리스트에서 제거해서 고아 상태로 만들 경우, post가 영속화 컨텍스트에서 사라진다.")
  void orphan_removal_test() {

    // Given
    USER_FIXTURE.addPost(POST_FIXTURE_1);
    USER_FIXTURE.addPost(POST_FIXTURE_2);
    entityManager.getTransaction().begin();
    entityManager.persist(USER_FIXTURE);
    entityManager.getTransaction().commit();

    // When
    entityManager.getTransaction().begin();
    USER_FIXTURE.removePostById(POST_FIXTURE_1.getId());
    entityManager.getTransaction().commit();
    // Then
    assertThat(entityManager.contains(POST_FIXTURE_1)).isFalse();
    assertThat(entityManager.contains(POST_FIXTURE_2)).isTrue();
  }

  @Test
  @DisplayName("영속화된 User,Post들을 컨텍스트에서 분리시킨 뒤, User만 find를 통해 새로 가져올 경우 user를 통해 Post들도 조회할 수 있다")
  void relation_detach_test() {

    // Given
    USER_FIXTURE.addPost(POST_FIXTURE_1);
    USER_FIXTURE.addPost(POST_FIXTURE_2);
    entityManager.getTransaction().begin();
    entityManager.persist(USER_FIXTURE);
    entityManager.getTransaction().commit();

    // When
    entityManager.detach(USER_FIXTURE);

    // Then
    var queriedUser = entityManager.find(User.class, USER_FIXTURE.getId());
    assertThat(queriedUser).isNotEqualTo(USER_FIXTURE);
    assertThat(queriedUser.getPosts()).hasSize(2);
  }

}
