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
public class CommentRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private Long postId;
    private Long commentId;

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
                .writer(user)
                .build();

        Comment comment = Comment.builder()
                .content("댓글")
                .post(post)
                .writer(user)
                .build();
        post.addComment(comment);

        userRepository.save(user).getId();
        postId = postRepository.save(post).getId();
        commentId = commentRepository.save(comment).getId();
    }

    @AfterEach
    void tearDown() {
        commentRepository.deleteAll();
        postRepository.deleteAll();
        userRepository.deleteAll();
    }

    @DisplayName("댓글 등록 확인")
    @Test
    void commentInsertTest() {
        assertThat(commentRepository.findById(commentId).isPresent()).isEqualTo(true);
    }

    @DisplayName("댓글 수정 확인")
    @Test
    void commentUpdateTest() {
        Comment findComment = commentRepository.findById(commentId).get();
        findComment.changeInfo("댓글수정");

        Optional<Comment> retrievedComment = commentRepository.findById(commentId);
        assertThat(retrievedComment.isPresent()).isEqualTo(true);
        assertThat(retrievedComment.get().getContent()).isEqualTo("댓글수정");
    }

    @DisplayName("특정 댓글 삭제 확인")
    @Test
    void commentDeleteTest() {
        Comment findComment = commentRepository.findById(commentId).get();
        commentRepository.delete(findComment);

        Optional<Comment> retrievedComment = commentRepository.findById(commentId);
        assertThat(retrievedComment.isPresent()).isEqualTo(false);
    }

    @DisplayName("게시글 삭제 시 관련 댓글 삭제 확인")
    @Test
    void postDeleteWithCommentTest() {
        Post findPost = postRepository.findById(postId).get();
        postRepository.delete(findPost);

        Optional<Comment> retrievedComment = commentRepository.findById(commentId);
        assertThat(retrievedComment.isPresent()).isEqualTo(false);
    }
}
