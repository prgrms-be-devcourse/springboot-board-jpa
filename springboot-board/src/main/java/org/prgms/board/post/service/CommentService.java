package org.prgms.board.post.service;

import org.prgms.board.common.exception.NotFoundException;
import org.prgms.board.common.exception.NotMatchException;
import org.prgms.board.domain.entity.Comment;
import org.prgms.board.domain.entity.Post;
import org.prgms.board.domain.entity.User;
import org.prgms.board.domain.repository.CommentRepository;
import org.prgms.board.domain.repository.PostRepository;
import org.prgms.board.domain.repository.UserRepository;
import org.prgms.board.post.dto.CommentRequest;
import org.prgms.board.post.dto.CommentResponse;
import org.prgms.board.user.dto.UserIdRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentService(PostRepository postRepository,
                          UserRepository userRepository,
                          CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Long addComment(Long postId, CommentRequest commentRequest) {
        User user = getUser(commentRequest.getUserId());
        Post post = getPost(postId);

        return commentRepository.save(Comment.builder()
            .content(commentRequest.getContent())
            .post(post)
            .writer(user)
            .build()).getId();
    }

    @Transactional
    public Long modifyComment(Long postId, Long commentId, CommentRequest commentRequest) {
        Comment comment = validate(postId, commentId, commentRequest.getUserId());
        comment.changeInfo(commentRequest.getContent());
        return comment.getId();
    }

    @Transactional
    public void removeComment(Long postId, Long commentId, UserIdRequest userIdRequest) {
        Comment comment = validate(postId, commentId, userIdRequest.getUserId());
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public CommentResponse getOneComment(Long commentId) {
        return new CommentResponse(getComment(commentId));
    }

    private Comment validate(Long postId, Long commentId, Long userId) {
        User user = getUser(userId);
        Post post = getPost(postId);
        Comment comment = getComment(commentId);

        if (!post.equals(comment.getPost())) {
            throw new NotMatchException("해당 게시글에 존재하지 않는 댓글입니다.");
        }

        if (!user.equals(comment.getWriter())) {
            throw new NotMatchException("해당 댓글에 접근 권한이 없습니다.");
        }

        return comment;
    }

    private Comment getComment(Long id) {
        return commentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("해당 댓글을 찾을 수 없습니다."));
    }

    private Post getPost(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("해당 게시글을 찾을 수 없습니다."));
    }

    private User getUser(Long id) {
        return userRepository.findByIdAndDeleted(id, false)
            .orElseThrow(() -> new NotFoundException("해당 사용자를 찾을 수 없습니다."));
    }
}
