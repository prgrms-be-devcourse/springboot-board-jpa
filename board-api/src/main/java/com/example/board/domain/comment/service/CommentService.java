package com.example.board.domain.comment.service;

import com.example.board.domain.comment.dto.CommentCreateRequest;
import com.example.board.domain.comment.dto.CommentResponse;
import com.example.board.domain.comment.dto.CommentUpdateRequest;
import com.example.board.domain.comment.entity.Comment;
import com.example.board.domain.comment.repository.CommentRepository;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.member.repository.MemberRepository;
import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.global.exception.CustomException;
import com.example.board.global.exception.ErrorCode;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public CommentResponse createComment(Long postId, Long writerId, CommentCreateRequest request) {
        Member writer = memberRepository.findById(writerId)
            .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));

        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        Comment parent = request.parentId() == null
            ? null
            : commentRepository.findById(request.parentId())
                .orElseThrow(() -> new CustomException(ErrorCode.PARENT_COMMENT_NOT_FOUND));

        Comment comment = new Comment(
            request.content(),
            post,
            writer,
            parent
        );
        commentRepository.save(comment);
        return CommentResponse.toDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findAllCommentsByPostId(Long postId) {
        Post post = postRepository.findById(postId)
            .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        List<Comment> comments = commentRepository.findAllOrderByParentIdAscNullsFirstByPost(post.getId());

        return CommentResponse.toDtoList(comments);
    }

    public CommentResponse updateComment(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        comment.update(request.content());
        return CommentResponse.toDto(comment);
    }

    public void deleteCommentByCommentId(Long id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        comment.delete();
    }
}
