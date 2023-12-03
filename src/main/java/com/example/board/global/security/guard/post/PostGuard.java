package com.example.board.global.security.guard.post;

import com.example.board.domain.post.entity.Post;
import com.example.board.domain.post.repository.PostRepository;
import com.example.board.global.exception.CustomException;
import com.example.board.global.exception.ErrorCode;
import com.example.board.global.security.guard.Guard;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.board.global.security.util.SecurityUtil.getCurrentUserId;

@Component
@RequiredArgsConstructor
public class PostGuard extends Guard {

    private final PostRepository postRepository;

    @Override
    protected boolean isResourceOwner(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.ACCESS_DENIED));

        Long memberId = getCurrentUserId()
            .orElseThrow(() -> new CustomException(ErrorCode.ACCESS_DENIED));

        return post.getMember().getId().equals(memberId);
    }
}
