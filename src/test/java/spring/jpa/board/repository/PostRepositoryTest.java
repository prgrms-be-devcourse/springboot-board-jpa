package spring.jpa.board.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.jpa.board.domain.Post;
import spring.jpa.board.domain.User;

@Slf4j
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
    User user = new User("강희정", 24);
    user.setHobby("낮잠");

    userRepository.save(user);

    Post post = new Post();
    post.setTitle("테스트");
    post.setContent("테스트중입니다.");
    post.setUser(user);

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
    User user = new User("강희정", 24);
    user.setHobby("낮잠");

    userRepository.save(user);

    Post post1 = new Post();
    post1.setTitle("테스트");
    post1.setContent("테스트중입니다.");
    post1.setUser(user);

    Post post2 = new Post();
    post2.setTitle("테스트");
    post2.setContent("테스트중입니다.");
    post2.setUser(user);

    //when
    postRepository.save(post1);
    postRepository.save(post2);

    //then
    List<Post> posts = postRepository.findPostsByUserId(user.getId());
    assertThat(posts, hasSize(2));

  }

}
