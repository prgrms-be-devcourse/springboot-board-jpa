package com.spring.board.springboard.post.domain.dto;

import com.spring.board.springboard.post.domain.Post;
import com.spring.board.springboard.user.domain.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record PostCreateRequestDto(@NotBlank(message = "제목을 입력해주세요.") String title,
                                   @NotBlank(message = "내용을 입력해주세요.") String content,
                                   @NotNull(message = "작성자가 없을 수 없습니다.") Integer memberId) {

    public Post toEntity(Member member) {
        return new Post(
                title,
                content,
                LocalDateTime.now(),
                member
        );
    }
}
