package org.prgms.board.post.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.board.common.exception.NotFoundException;
import org.prgms.board.domain.entity.Comment;
import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.prgms.board.domain.repository.CommentRepository;
import org.prgms.board.domain.repository.PostRepository;
import org.prgms.board.domain.repository.UserRepository;
import org.prgms.board.post.dto.CommentRequest;
import org.prgms.board.post.dto.CommentResponse;
import org.prgms.board.user.dto.UserIdRequest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
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
        post.addComment(comment);
    }

    @DisplayName("댓글 등록 확인")
    @Test
    void addCommentTest() {
        CommentRequest commentRequest = new CommentRequest(1L, "comment");
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(commentRepository.save(any())).thenReturn(comment);

        Long commentId = commentService.writeComment(post.getId(), commentRequest);

        assertThat(commentId).isEqualTo(comment.getId());
    }

    @DisplayName("댓글 수정 확인")
    @Test
    void modifyCommentTest() {
        CommentRequest commentRequest = new CommentRequest(1L, "comment2");
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        commentService.modifyComment(post.getId(), comment.getId(), commentRequest);

        CommentResponse retrievedComment = commentService.getComment(comment.getId());
        assertThat(retrievedComment.getAuthor()).isEqualTo(commentRequest.getUserId());
        assertThat(retrievedComment.getContent()).isEqualTo(commentRequest.getContent());
    }

    @DisplayName("댓글 삭제 확인")
    @Test
    void removeCommentTest() {
        UserIdRequest userIdRequest = new UserIdRequest(1L);
        when(userRepository.findByIdAndDeleted(anyLong(), anyBoolean())).thenReturn(Optional.of(user));
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));
        when(commentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> commentService.removeComment(post.getId(), comment.getId(), userIdRequest))
            .isInstanceOf(NotFoundException.class)
            .hasMessageContaining("해당 댓글을 찾을 수 없습니다.");
    }
}