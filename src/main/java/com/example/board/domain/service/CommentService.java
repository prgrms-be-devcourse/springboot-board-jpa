package com.example.board.domain.service;

import com.example.board.domain.entity.Comment;
import com.example.board.domain.entity.Post;
import com.example.board.domain.entity.User;
import com.example.board.domain.entity.repository.CommentRepository;
import com.example.board.dto.comment.CommentRequestDto;
import com.example.board.exception.comment.NotFoundCommentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;

    private final UserService userService;

    @Transactional
    public Long createComment(CommentRequestDto requestDto) {
        User user = userService.getUser(requestDto.getUserId());
        Post post = postService.getPost(requestDto.getPostId());
        Comment comment = requestDto.toEntity(user, post);
        return commentRepository.save(comment).getId();
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = getComment(commentId);
        commentRepository.delete(comment);
    }

    private Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundCommentException("아이디와 일치하는 댓글을 찾을 수 없습니다"));
    }
}
