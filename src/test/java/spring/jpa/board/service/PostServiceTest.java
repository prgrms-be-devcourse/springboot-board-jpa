package spring.jpa.board.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.util.List;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
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
@Slf4j
class PostServiceTest {

  @Autowired
  PostRepository postRepository;

  @Autowired
  PostService postService;

  @Autowired
  UserRepository userRepository;


  @AfterEach
  public void cleanUp() {
    postService.deleteAll();
    userRepository.deleteAll();
  }


  @Test
  @DisplayName("게시글을 생성할 수 있다.")
  public void savePostTest() throws NotFoundException {
    //given
    User user = new User("강희정", 24);
    user.setHobby("낮잠");
    userRepository.save(user);

    PostCreateRequest postCreateRequest = new PostCreateRequest("테스트", "테스트 중입니다.", user.getId());

    //when
    postService.save(postCreateRequest);

    //then
    List<Post> all = postRepository.findAll();
    assertThat(all, hasSize(1));
  }


  @Test
  @DisplayName("id로 게시글을 찾을 수 있다.")
  public void findByIdTest() throws NotFoundException {
    //given
    User user = new User("강희정", 24);
    user.setHobby("낮잠");
    userRepository.save(user);

    PostCreateRequest postCreateRequest = new PostCreateRequest("테스트", "테스트 중입니다.", user.getId());
    Long id = postService.save(postCreateRequest);

    //when
    PostFindRequest findPost = postService.findById(id);

    //then
    assertThat(findPost, notNullValue());

  }


  @Test
  @DisplayName("모든 게시글 목록을 읽을 수 있다.")
  public void findAllTest() throws NotFoundException {
    //given
    User user = new User("강희정", 24);
    user.setHobby("낮잠");
    userRepository.save(user);

    PostCreateRequest postCreateRequest1 = new PostCreateRequest("테스트", "테스트 중입니다.", user.getId());
    postService.save(postCreateRequest1);

    PostCreateRequest postCreateRequest2 = new PostCreateRequest("테스트", "테스트 중입니다.", user.getId());
    postService.save(postCreateRequest2);

    //when
    List<Post> all = postRepository.findAll();

    //then
    assertThat(all, hasSize(2));
  }


  @Test
  @DisplayName("사용자 id로 게시글 목록을 찾을 수 있다.")
  public void findByUserIdTest() throws NotFoundException {
    //given
    User user = new User("강희정", 24);
    user.setHobby("낮잠");
    userRepository.save(user);

    PostCreateRequest postCreateRequest1 = new PostCreateRequest("테스트", "테스트 중입니다.", user.getId());
    postService.save(postCreateRequest1);

    PostCreateRequest postCreateRequest2 = new PostCreateRequest("테스트", "테스트 중입니다.", user.getId());
    postService.save(postCreateRequest2);

    //when 
    List<PostFindRequest> all = postService.findByUserId(user.getId());

    //then 
    assertThat(all, hasSize(2));
  }


  @Test
  @DisplayName("게시글을 수정할 수 있다.")
  public void updatePostTest() throws NotFoundException {
    //given
    User user = new User("강희정", 24);
    user.setHobby("낮잠");
    userRepository.save(user);

    PostCreateRequest postCreateRequest1 =
        new PostCreateRequest("수정 전 제목", "수정 전 테스트중입니다.", user.getId());
    postService.save(postCreateRequest1);

    //when
    String title = "수정한 제목입니다.";
    PostCreateRequest postCreateRequest2 =
        new PostCreateRequest(title, postCreateRequest1.content(), user.getId());
    Long id = postService.save(postCreateRequest2);

    //then
    PostFindRequest findPost = postService.findById(id);
    assertThat(findPost.title(), is(title));
  }
}
