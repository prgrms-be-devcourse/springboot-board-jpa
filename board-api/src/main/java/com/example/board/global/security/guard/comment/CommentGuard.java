package com.example.board.global.security.guard.comment;

import com.example.board.domain.comment.entity.Comment;
import com.example.board.domain.comment.repository.CommentRepository;
import com.example.board.global.exception.CustomException;
import com.example.board.global.exception.ErrorCode;
import com.example.board.global.security.guard.Guard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentGuard extends Guard {

    private final CommentRepository commentRepository;

    @Override
    protected boolean isResourceOwner(Long id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.ACCESS_DENIED));
        return comment.getId().equals(id);
    }
}
