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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public CommentResponse createComment(CommentSaveRequest saveRequest) {
        Users user = userRepository.findById(saveRequest.getUserId())
                .orElseThrow(() -> new NoSuchElementException("없는 사용자입니다."));
        Post post = postRepository.findById(saveRequest.getPostId())
                .orElseThrow(() -> new NoSuchElementException("없는 게시글입니다."));
        Comment comment = commentRepository.save(
                new Comment(user, post, saveRequest.getContent())
        );
        return CommentResponse.fromEntity(comment);
    }

    @Transactional
    public CommentResponse updateComment(CommentUpdateRequest updateRequest, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("없는 댓글입니다."));
        comment.updateComment(updateRequest.getContent());
        return CommentResponse.fromEntity(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findCommentsByUserId(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("없는 사용자입니다."));
        List<Comment> comments = commentRepository.findByUser(user);
        return CommentResponse.fromEntities(comments);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("없는 게시글입니다."));
        List<Comment> comments = commentRepository.findByPost(post);
        return CommentResponse.fromEntities(comments);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}