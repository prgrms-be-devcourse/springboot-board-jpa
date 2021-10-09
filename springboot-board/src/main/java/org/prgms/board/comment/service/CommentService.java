package org.prgms.board.comment.service;

import org.prgms.board.comment.dto.CommentRequest;
import org.prgms.board.comment.dto.CommentResponse;
import org.prgms.board.domain.entity.Comment;
import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.prgms.board.domain.repository.CommentRepository;
import org.prgms.board.domain.repository.PostRepository;
import org.prgms.board.domain.repository.UserRepository;
import org.prgms.board.exception.NotFoundException;
import org.prgms.board.exception.NotMatchException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Long addComment(Long userId, Long postId, CommentRequest commentRequest) {
        User user = getUser(userId);
        Post post = getPost(postId);

        return commentRepository.save(Comment.builder()
                .content(commentRequest.getContent())
                .author(user.getName())
                .post(post)
                .user(user)
                .build()).getId();
    }

    @Transactional
    public Long modifyComment(Long userId, Long commentId, CommentRequest commentRequest) {
        User user = getUser(userId);
        Comment comment = getComment(commentId);

        if (!user.equals(comment.getUser())) {
            throw new NotMatchException("자신의 댓글만 수정할 수 있습니다.");
        }

        comment.changeInfo(commentRequest.getContent());
        return comment.getId();
    }

    @Transactional
    public void removeComment(Long userId, Long commentId) {
        User user = getUser(userId);
        Comment comment = getComment(commentId);

        if (!user.equals(comment.getUser())) {
            throw new NotMatchException("자신의 댓글만 삭제할 수 있습니다.");
        }

        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public CommentResponse getOneComment(Long commentId) {
        return new CommentResponse(getComment(commentId));
    }

    private Comment getComment(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 댓글을 찾을 수 없습니다."));
    }

    private Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 게시글을 찾을 수 없습니다."));
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
    }
}
