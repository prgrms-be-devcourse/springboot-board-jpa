package com.prgrms.board.repository;

import com.prgrms.board.domain.Post;
import com.prgrms.board.domain.User;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private Long userId;

    @BeforeEach
    void setUp(){
        User user = User.builder()
                .name("Kimdahee")
                .age(99)
                .hobby("making")
                .build();

        userId = userRepository.save(user).getId();
    }

    @AfterEach
    void tearDown(){
        postRepository.deleteAll();
    }

    @DisplayName("Post 생성 확인")
    @Test
    void createPostTest() {
        //given
        User user = userRepository.findById(userId).get();
        Post post = Post.builder()
                .user(user)
                .title("데브코스 TIL")
                .content("오늘의 느낀점:재밌다.")
                .build();

        //when
        postRepository.save(post);

        //then
        Post findPost = postRepository.findById(post.getId()).get();

        assertThat(findPost.getUser().getName()).isEqualTo(user.getName());
        assertThat(findPost.getUser().getAge()).isEqualTo(user.getAge());
        assertThat(findPost.getUser().getHobby()).isEqualTo(user.getHobby());

        assertThat(findPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(findPost.getContent()).isEqualTo(post.getContent());
        assertThat(findPost.getCreatedAt()).isEqualTo(post.getCreatedAt());
        assertThat(findPost.getUpdatedAt()).isEqualTo(post.getUpdatedAt());
    }

    @DisplayName("Post 전체 목록 조회")
    @Test
    void findPostAllTest() {
        //given
        User user = userRepository.findById(userId).get();
        Post post1 = Post.builder()
                .user(user)
                .title("데브코스 TIL1")
                .content("오늘의 느낀점:재밌다.")
                .build();

        Post post2 = Post.builder()
                .user(user)
                .title("데브코스 TIL2")
                .content("오늘의 느낀점:재밌다.")
                .build();

        //when
        postRepository.save(post1);
        postRepository.save(post2);

        //then
        assertThat(postRepository.findAll().size()).isEqualTo(2);
    }

    @DisplayName("Post 수정 확인")
    @Test
    void updatePostTest() {
        //given
        User user = userRepository.findById(userId).get();
        Post post = Post.builder()
                .user(user)
                .title("데브코스 TIL")
                .content("오늘의 느낀점:재밌다.")
                .build();

        //when
        postRepository.save(post);
        post.changeInfo("데브코스 TIL 2일차", "오늘의 느낀점:어렵다.");

        //then
        Post findPost = postRepository.findById(post.getId()).get();

        assertThat(findPost.getUser().getName()).isEqualTo(user.getName());
        assertThat(findPost.getUser().getAge()).isEqualTo(user.getAge());
        assertThat(findPost.getUser().getHobby()).isEqualTo(user.getHobby());

        assertThat(findPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(findPost.getContent()).isEqualTo(post.getContent());
        assertThat(findPost.getCreatedAt()).isEqualTo(post.getCreatedAt());
        assertThat(findPost.getUpdatedAt()).isEqualTo(post.getUpdatedAt());
    }

    @DisplayName("Post 삭제 확인")
    @Test
    void deletePostTest() {
        //given
        User user = userRepository.findById(userId).get();
        Post post = Post.builder()
                .user(user)
                .title("데브코스 TIL")
                .content("오늘의 느낀점:재밌다.")
                .build();

        //when
        postRepository.save(post);
        postRepository.delete(post);

        //then
        Optional<Post> findPost = postRepository.findById(post.getId());
        assertThat(findPost.isPresent()).isEqualTo(false);
    }

}