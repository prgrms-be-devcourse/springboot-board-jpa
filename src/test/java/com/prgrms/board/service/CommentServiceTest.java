package com.prgrms.board.service;

import com.prgrms.board.domain.Comment;
import com.prgrms.board.domain.Post;
import com.prgrms.board.domain.Users;
import com.prgrms.board.dto.comment.CommentResponse;
import com.prgrms.board.dto.comment.CommentSaveRequest;
import com.prgrms.board.dto.comment.CommentUpdateRequest;
import com.prgrms.board.repository.CommentRepository;
import com.prgrms.board.repository.PostRepository;
import com.prgrms.board.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CommentServiceTest {

    private CommentService commentService;
    private CommentRepository commentRepository;
    private UserRepository userRepository;
    private PostRepository postRepository;

    @BeforeEach
    void setUp() {
        commentRepository = mock(CommentRepository.class);
        userRepository = mock(UserRepository.class);
        postRepository = mock(PostRepository.class);
        commentService = new CommentService(commentRepository, userRepository, postRepository);
    }

    @DisplayName("댓글 작성 테스트")
    @Test
    void createComment() {
        // Given
        Long userId = 1L;
        Long postId = 1234L;
        String content = "테스트 댓글";
        Users user = new Users("test@test.com", "테스트 사용자", 30);
        Post post = new Post(user, "테스트 제목", "테스트 내용");
        CommentSaveRequest saveRequest = new CommentSaveRequest(userId, postId, content);

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.save(any(Comment.class))).thenAnswer(invocation -> {
            return invocation.<Comment>getArgument(0);
        });

        // When
        CommentResponse createdComment = commentService.createComment(saveRequest);
        // Then
        assertThat(createdComment).isNotNull();
        assertThat(createdComment.getContent()).isEqualTo(content);
        verify(userRepository, times(1)).findById(userId);
        verify(postRepository, times(1)).findById(postId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @DisplayName("댓글 수정 테스트")
    @Test
    void updateComment() {
        // Given
        Long commentId = 1L;
        String updatedContent = "수정된 댓글 내용";
        Users user = new Users("test@test.com", "lee", 10);
        Post post = new Post(user, "test title", "test content");
        Comment comment = new Comment(user, post, "test 댓글");
        CommentUpdateRequest updateRequest = new CommentUpdateRequest(1L, 1L, updatedContent);
        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));
        // When
        CommentResponse updatedComment = commentService.updateComment(updateRequest, commentId);
        // Then
        assertThat(updatedComment).isNotNull();
        assertThat(updatedComment.getContent()).isEqualTo(updatedContent);
        verify(commentRepository, times(1)).findById(commentId);
    }

    @DisplayName("사용자가 작성한 모든 댓글 가져오기 테스트")
    @Test
    void findCommentsByUserId() {
        // Given
        Long userId = 1L;
        Users user = new Users("test@test.com", "테스트 사용자", 30);
        Comment comment1 = new Comment(user, new Post(user, "test title", "test content"), "댓글1");
        Comment comment2 = new Comment(user, new Post(user, "test title2", "test content2"), "댓글2");
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(commentRepository.findByUser(user)).thenReturn(Arrays.asList(comment1, comment2));
        // When
        List<CommentResponse> comments = commentService.findCommentsByUserId(userId);
        // Then
        assertThat(comments).hasSize(2);
        verify(userRepository, times(1)).findById(userId);
        verify(commentRepository, times(1)).findByUser(user);
    }

    @DisplayName("특정 게시글의 모든 댓글 가져오기 테스트")
    @Test
    void findCommentsByPostId() {
        // Given
        Long postId = 1L;
        Users user = new Users("test@test.com", "테스트 사용자", 30);
        Post post = new Post(user, "test title", "test content");
        Comment comment1 = new Comment(new Users("test@test.com", "lee", 10), post, "댓글1");
        Comment comment2 = new Comment(new Users("test2@test.com", "test", 10), post, "댓글2");
        when(postRepository.findById(postId)).thenReturn(Optional.of(post));
        when(commentRepository.findByPost(post)).thenReturn(Arrays.asList(comment1, comment2));
        // When
        List<CommentResponse> comments = commentService.findCommentsByPostId(postId);
        // Then
        assertThat(comments).hasSize(2);
        verify(postRepository, times(1)).findById(postId);
        verify(commentRepository, times(1)).findByPost(post);
    }

    @DisplayName("댓글 삭제 테스트")
    @Test
    void deleteComment() {
        // Given
        Long commentId = 1L;
        // When
        commentService.deleteComment(commentId);
        verify(commentRepository, times(1)).deleteById(commentId);
    }
}
