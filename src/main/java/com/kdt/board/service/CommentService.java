package com.kdt.board.service;

import com.kdt.board.domain.Comment;
import com.kdt.board.domain.Post;
import com.kdt.board.domain.User;
import com.kdt.board.dto.comment.CommentRequest;
import com.kdt.board.dto.comment.CommentResponse;
import com.kdt.board.repository.CommentRepository;
import com.kdt.board.repository.PostRepository;
import com.kdt.board.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentService(PostRepository postRepository, CommentRepository commentRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
    }

    // question : baseEntity의 필드는 왜 builder가 안 되지?
    @Transactional
    public Long createComment(Long postId, CommentRequest commentRequest) {
        Post post = postRepository.getById(postId);
        User user = userRepository.findByName(commentRequest.getUserName())
                .orElseGet(() -> User.builder()
                        .name(commentRequest.getUserName())
                        .build());
        Comment newComment = Comment.builder()
                .post(post)
                .content(commentRequest.getContent())
                .user(user)
                .build();
        userRepository.save(user);
        post.addComment(newComment);
        return commentRepository.save(newComment).getId();
    }

    // TODO: 2021-10-24 name까지 수정하게끔 변경
    @Transactional
    public CommentResponse updateComment(Long commentId, CommentRequest commentRequest) {
        Comment comment = commentRepository.findById(commentId).get();
        comment.changeContent(commentRequest.getContent());
        return new CommentResponse(commentRepository.save(comment));
    }

    // TODO: 2021-10-25 removeComment() 추가
    @Transactional
    public void deleteComment(Long postId, Long commentId) {
        Comment comment = commentRepository.getById(commentId);
        commentRepository.delete(comment);
        postRepository.getById(postId).getComments().remove(comment);
    }

    @Transactional
    public Optional<Comment> findOneComment(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Transactional
    public List<Comment> findAllByUser(User user) {
        return commentRepository.findAllByUser(user);
    }
}
