package com.eden6187.jpaboard.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import com.eden6187.jpaboard.model.Post;
import com.eden6187.jpaboard.model.User;
import com.eden6187.jpaboard.test_data.PostMockData;
import com.eden6187.jpaboard.test_data.UserMockData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
public class PostRepositoryTest {

  private static final User user = User.builder()
      .age(UserMockData.TEST_AGE)
      .hobby(UserMockData.TEST_HOBBY)
      .name(UserMockData.TEST_NAME)
      .build();
  private static final Post post = Post.builder()
      .content(PostMockData.TEST_CONTENT)
      .title(PostMockData.TEST_TITLE)
      .build();
  @Autowired
  PostRepository postRepository;
  @Autowired
  UserRepository userRepository;

  @Test
  @Transactional
  @DisplayName("Post와 User는 다대일의 양방향 연관관계를 가진다.")
  void savePostTest() {
    userRepository.save(user);
    post.setUser(user);
    Post savedPost = postRepository.save(post);

    // DB에 의한 POST의 ID 할당 확인
    assertThat(savedPost.getId(), is(not(nullValue())));

    // Post와 User의 양방향 연관관계 확인
    // Post(Many) to User(One)
    assertThat(user.getPosts(), hasItem(savedPost));
    assertThat(savedPost.getUser(), is(user));
  }
}
