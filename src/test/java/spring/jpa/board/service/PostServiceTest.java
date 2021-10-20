package spring.jpa.board.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import javassist.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import spring.jpa.board.domain.Post;
import spring.jpa.board.domain.User;
import spring.jpa.board.dto.post.PostCreateRequest;
import spring.jpa.board.dto.post.PostFindRequest;
import spring.jpa.board.repository.PostRepository;
import spring.jpa.board.repository.UserRepository;

@SpringBootTest
class PostServiceTest {

  @Autowired
  PostRepository postRepository;

  @Autowired
  PostService postService;

  @Autowired
  UserRepository userRepository;

  User user;
  PostCreateRequest postCreateRequest1;
  PostCreateRequest postCreateRequest2;
  Long saveId1;

  @BeforeEach
  public void setInfo() throws NotFoundException {
    user = new User("강희정", 24, "낮잠");
    userRepository.save(user);

    postCreateRequest1 = new PostCreateRequest("테스트", "테스트 중입니다.", user.getId());
    saveId1 = postService.save(postCreateRequest1);

    postCreateRequest2 = new PostCreateRequest("테스트", "테스트 중입니다.", user.getId());
    postService.save(postCreateRequest2);

  }


  @AfterEach
  public void cleanUp() {
    postService.deleteAll();
    userRepository.deleteAll();
  }


  @Test
  @DisplayName("게시글을 생성할 수 있다.")
  public void savePostTest() {
    //then
    List<Post> all = postRepository.findAll();
    assertThat(all, hasSize(2));
  }


  @Test
  @DisplayName("id로 게시글을 찾을 수 있다.")
  public void findByIdTest() throws NotFoundException {
    //when
    PostFindRequest findPost = postService.findById(saveId1);

    //then
    assertThat(findPost, notNullValue());

  }


  @Test
  @DisplayName("모든 게시글 목록을 읽을 수 있다.")
  public void findAllTest() {
    //when
    List<Post> all = postRepository.findAll();

    //then
    assertThat(all, hasSize(2));
  }


  @Test
  @DisplayName("사용자 id로 게시글 목록을 찾을 수 있다.")
  public void findByUserIdTest() {
    //when 
    List<PostFindRequest> all = postService.findByUserId(user.getId());

    //then 
    assertThat(all, hasSize(2));
  }


  @Test
  @DisplayName("게시글을 수정할 수 있다.")
  public void updatePostTest() throws NotFoundException {
    //when
    String title = "수정한 제목입니다.";
    Long id = postService.save(
        new PostCreateRequest(title, postCreateRequest1.content(), user.getId()));

    //then
    PostFindRequest findPost = postService.findById(id);
    assertThat(findPost.title(), is(title));
  }
}
