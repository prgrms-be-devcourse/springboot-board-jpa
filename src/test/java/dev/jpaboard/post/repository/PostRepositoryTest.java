package dev.jpaboard.post.repository;

import dev.jpaboard.post.domain.Post;
import dev.jpaboard.user.domain.User;
import dev.jpaboard.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@DataJpaTest
class PostRepositoryTest {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private UserRepository userRepository;

  @Test
  void findPostByUserTest() {
    //given
    User user = User.builder()
            .email("qkrdmswl123@naver.com")
            .name("바보은지")
            .password("Qkr123@234")
            .hobby("박은지 바보")
            .age(11)
            .build();
    User 박은지 = userRepository.save(user);
    Post 박 = createPost("제목1", "내용1", 박은지);
    Post 은 = createPost("제목2", "내용2", 박은지);
    Post 지 = createPost("제목3", "내용3", 박은지);

    //when
    postRepository.saveAll(List.of(박, 은, 지));

    //then
    Page<Post> posts = postRepository.findPostByUserId(박은지.getId(), PageRequest.of(0, 1));

    Assertions.assertThat(posts.getTotalPages()).isEqualTo(3);
  }

  Post createPost(String 제목, String 내용, User 유저) {
    return Post.builder()
            .title(제목)
            .content(내용)
            .user(유저)
            .build();
  }

}
