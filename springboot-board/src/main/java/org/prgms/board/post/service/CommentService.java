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
    public Long writeComment(Long postId, CommentRequest commentRequest) {
        User user = activeUser(commentRequest.getUserId());
        Post post = findPost(postId);

		Comment comment = Comment.builder()
								 .content(commentRequest.getContent())
								 .post(post)
								 .writer(user)
								 .build();
		post.addComment(comment);

		return comment.getId();
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
    public CommentResponse getComment(Long commentId) {
        return new CommentResponse(findComment(commentId));
    }

    private Comment validate(Long postId, Long commentId, Long userId) {
        User user = activeUser(userId);
        Post post = findPost(postId);
        Comment comment = findComment(commentId);

        if (!post.equals(comment.getPost())) {
            throw new NotMatchException(String.format("해당 %d번 게시글에 존재하지 않는 댓글입니다.", postId));
        }

        if (!user.equals(comment.getWriter())) {
            throw new NotMatchException(String.format("해당 %d번 댓글을 작성한 사용자가 아닙니다.", commentId));
        }

        return comment;
    }

    private Comment findComment(Long id) {
        return commentRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("해당 %d번 댓글을 찾을 수 없습니다.", id)));
    }

    private Post findPost(Long id) {
        return postRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("해당 %d번 게시글을 찾을 수 없습니다.", id)));
    }

    private User activeUser(Long id) {
        return userRepository.findByIdAndDeleted(id, false)
            .orElseThrow(() -> new NotFoundException(String.format("해당 %d번 사용자를 찾을 수 없습니다.", id)));
    }
}
