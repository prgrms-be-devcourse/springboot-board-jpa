package org.prgms.board.domain.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.board.domain.entity.Comment;
import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Long userId;
    private Long postId;

    @BeforeEach
    void setUp() {
        User user = User.builder()
                .name("김부희")
                .age(26)
                .hobby("만들기")
                .build();

        Post post = Post.builder()
                .title("제목")
                .content("내용")
                .author(user.getName())
                .user(user)
                .build();

        userId = userRepository.save(user).getId();
        postId = postRepository.save(post).getId();
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("게시글 등록 확인")
    @Test
    void postInsertTest() {
        assertThat(postRepository.findById(postId).isPresent()).isEqualTo(true);
    }

    @DisplayName("게시글 수정 확인")
    @Test
    @Transactional
    void postUpdateTest() {
        Post findPost = postRepository.findById(postId).get();
        findPost.changeInfo("제목수정", "내용수정");

        Optional<Post> retrievedPost = postRepository.findById(postId);
        assertThat(retrievedPost.isPresent()).isEqualTo(true);
        assertThat(retrievedPost.get().getAuthor()).isEqualTo("김부희");
        assertThat(retrievedPost.get().getTitle()).isEqualTo("제목수정");
        assertThat(retrievedPost.get().getContent()).isEqualTo("내용수정");
    }

    @DisplayName("특정 게시글 삭제 확인")
    @Test
    @Transactional
    void postDeleteTest() {
        Post findPost = postRepository.findById(postId).get();
        postRepository.delete(findPost);

        Optional<Post> retrievedPost = postRepository.findById(postId);
        assertThat(retrievedPost.isPresent()).isEqualTo(false);
    }

    @DisplayName("모든 게시글 조회 확인")
    @Test
    void postFindAllTest() {
        User findUser = userRepository.findById(userId).get();

        Post post = Post.builder()
                .title("제목2")
                .content("내용2")
                .author(findUser.getName())
                .user(findUser)
                .build();
        postRepository.save(post);

        assertThat(postRepository.findAll().size()).isEqualTo(2);
    }

    @DisplayName("게시글 상세 조회 & 댓글 조회 확인")
    @Test
    void postFindByIdTest() {
        User findUser = userRepository.findById(userId).get();
        Post findPost = postRepository.findById(postId).get();

        Comment comment = Comment.builder()
                .content("댓글")
                .author(findUser.getName())
                .post(findPost)
                .user(findUser)
                .build();

        findPost.addComment(comment);
        commentRepository.save(comment);

        Optional<Post> retrievedPost = postRepository.findById(postId);
        assertThat(retrievedPost.isPresent()).isEqualTo(true);
        assertThat(retrievedPost.get().getComments().get(0).getContent()).isEqualTo("댓글");
    }
}