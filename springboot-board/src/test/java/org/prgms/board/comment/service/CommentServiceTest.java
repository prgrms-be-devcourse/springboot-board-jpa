package org.prgms.board.comment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.board.comment.dto.CommentRequest;
import org.prgms.board.comment.dto.CommentResponse;
import org.prgms.board.domain.entity.Comment;
import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.prgms.board.domain.repository.CommentRepository;
import org.prgms.board.domain.repository.PostRepository;
import org.prgms.board.domain.repository.UserRepository;
import org.prgms.board.exception.NotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommentServiceTest {
    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    private User user;
    private Post post;
    private Comment comment;

    @BeforeEach
    void setUp() {
        user = User.builder()
            .id(1L)
            .name("buhee")
            .age(26)
            .hobby("making")
            .build();

        post = Post.builder()
            .id(1L)
            .title("title")
            .content("content")
            .writer(user)
            .build();

        comment = Comment.builder()
            .id(1L)
            .content("comment")
            .post(post)
            .writer(user)
            .build();
    }

    @DisplayName("댓글 등록 확인")
    @Test
    void addCommentTest() {
        CommentRequest commentRequest = new CommentRequest("comment");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(commentRepository.save(any())).thenReturn(comment);

        Long commentId = commentService.addComment(user.getId(), post.getId(), commentRequest);
        assertThat(commentId).isEqualTo(comment.getId());
    }

    @DisplayName("댓글 수정 확인")
    @Test
    void modifyCommentTest() {
        CommentRequest commentRequest = new CommentRequest("comment2");
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        commentService.modifyComment(user.getId(), comment.getId(), commentRequest);

        CommentResponse retrievedComment = commentService.getOneComment(comment.getId());
        assertThat(retrievedComment.getContent()).isEqualTo("comment2");
        assertThat(retrievedComment.getAuthor()).isEqualTo("buhee");
    }

    @DisplayName("댓글 삭제 확인")
    @Test
    void removeCommentTest() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.removeComment(user.getId(), comment.getId()))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining("해당 댓글을 찾을 수 없습니다.");
    }
}