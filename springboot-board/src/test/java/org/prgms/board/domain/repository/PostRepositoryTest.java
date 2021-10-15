package org.prgms.board.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class PostRepositoryTest {
    private static final String UPDATE_TITLE = "제목 수정";
    private static final String UPDATE_CONTENT = "내용 수정";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    private User user;
    private Post post;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();

        user = User.builder()
            .name("김부희")
            .age(26)
            .hobby("만들기")
            .build();

        post = Post.builder()
            .title("제목")
            .content("내용")
            .writer(user)
            .build();

        userRepository.save(user);
        postRepository.save(post);
    }

    @DisplayName("게시글 등록 확인")
    @Test
    void postInsertTest() {
        assertThat(postRepository.findById(post.getId()).isPresent()).isEqualTo(true);
    }

    @DisplayName("게시글 수정 확인")
    @Test
    void postUpdateTest() {
        Post findPost = postRepository.findById(post.getId()).get();
        findPost.changeInfo(UPDATE_TITLE, UPDATE_CONTENT);

        Optional<Post> retrievedPost = postRepository.findById(post.getId());
        assertThat(retrievedPost.isPresent()).isEqualTo(true);
        assertThat(retrievedPost.get().getTitle()).isEqualTo(UPDATE_TITLE);
        assertThat(retrievedPost.get().getContent()).isEqualTo(UPDATE_CONTENT);
    }

    @DisplayName("특정 게시글 삭제 확인")
    @Test
    void postDeleteTest() {
        Post findPost = postRepository.findById(post.getId()).get();
        postRepository.delete(findPost);

        Optional<Post> retrievedPost = postRepository.findById(post.getId());
        assertThat(retrievedPost.isPresent()).isEqualTo(false);
    }

    @DisplayName("모든 게시글 조회 확인")
    @Test
    void postFindAllTest() {
        assertThat(postRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("게시글 상세 조회 확인")
    @Test
    void postFindByIdTest() {
        Optional<Post> retrievedPost = postRepository.findById(post.getId());
        assertThat(retrievedPost.isPresent()).isEqualTo(true);
        assertThat(retrievedPost.get().getTitle()).isEqualTo(post.getTitle());
        assertThat(retrievedPost.get().getContent()).isEqualTo(post.getContent());
        assertThat(retrievedPost.get().getWriter()).isEqualTo(post.getWriter());

    }
}