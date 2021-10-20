package spring.jpa.board.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.jpa.board.domain.Post;
import spring.jpa.board.domain.User;

@SpringBootTest
class PostRepositoryTest {

  @Autowired
  PostRepository postRepository;

  @Autowired
  UserRepository userRepository;

  @AfterEach
  public void cleanUp() {
    postRepository.deleteAll();
    userRepository.deleteAll();
  }


  @Test
  @DisplayName("게시글을 생성할 수 있다.")
  public void postCreateTest() {
    //given
    User user = new User("강희정", 24, "낮잠");
    userRepository.save(user);

    Post post = new Post("테스트", "테스트중입니다.", user);

    //when
    postRepository.save(post);

    //then
    List<Post> posts = postRepository.findAll();
    assertThat(posts, hasSize(1));
  }


  @Test
  @DisplayName("사용자가 작성한 게시글 목록을 가져올 수 있다.")
  public void findPostByUserIdTest() {
    //given
    User user = new User("강희정", 24, "낮잠");
    userRepository.save(user);

    Post post1 = new Post("테스트", "테스트중입니다.", user);
    Post post2 = new Post("테스트", "테스트중입니다.", user);
    postRepository.save(post1);
    postRepository.save(post2);

    //when
    List<Post> posts = postRepository.findPostsByUserId(user.getId());

    //then
    assertThat(posts, hasSize(2));

  }

}
