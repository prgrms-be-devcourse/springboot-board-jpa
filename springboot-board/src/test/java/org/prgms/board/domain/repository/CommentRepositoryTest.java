package org.prgms.board.domain.repository;

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
    private static final String UPDATE_COMMENT = "댓글 수정";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    private User user;
    private Post post;
    private Comment comment;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        commentRepository.deleteAll();

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

        comment = Comment.builder()
            .content("댓글")
            .post(post)
            .writer(user)
            .build();

        post.addComment(comment);

        userRepository.save(user);
        postRepository.save(post);
        commentRepository.save(comment);
    }

    @DisplayName("댓글 등록 확인")
    @Test
    void commentInsertTest() {
        assertThat(commentRepository.findById(comment.getId()).isPresent()).isEqualTo(true);
    }

    @DisplayName("댓글 수정 확인")
    @Test
    void commentUpdateTest() {
        comment.changeInfo(UPDATE_COMMENT);

        Optional<Comment> retrievedComment = commentRepository.findById(comment.getId());
        assertThat(retrievedComment.isPresent()).isEqualTo(true);
        assertThat(retrievedComment.get().getContent()).isEqualTo(UPDATE_COMMENT);
    }

    @DisplayName("특정 댓글 삭제 확인")
    @Test
    void commentDeleteTest() {
        commentRepository.delete(comment);

        Optional<Comment> retrievedComment = commentRepository.findById(comment.getId());
        assertThat(retrievedComment.isPresent()).isEqualTo(false);
    }

    @DisplayName("게시글 삭제 시 관련 댓글 삭제 확인")
    @Test
    void postDeleteWithCommentTest() {
        postRepository.delete(post);

        Optional<Comment> retrievedComment = commentRepository.findById(comment.getId());
        assertThat(retrievedComment.isPresent()).isEqualTo(false);
    }
}
