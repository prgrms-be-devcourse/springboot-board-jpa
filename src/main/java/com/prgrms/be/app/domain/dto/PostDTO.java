package com.prgrms.be.app.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PostDTO {

    @Getter
    @AllArgsConstructor
    public static class CreateRequest {
        @NotBlank(message = "게시글의 제목을 기입해야 합니다.")
        @Size(min = 1, max = 20)
        private String title;

        @NotBlank(message = "게시글의 내용을 기입해야 합니다.")
        @Column(columnDefinition = "TEXT")
        private String content;

        @NotNull
        private Long userId;
    }

    @Getter
    @AllArgsConstructor
    public static class UpdateRequest {
        @NotBlank(message = "게시글의 제목을 빈 상태로 수정할 수 없습니다.")
        @Size(min = 1, max = 20)
        private String title;

        @NotBlank(message = "게시글의 내용을 빈 상태로 수정할 수 없습니다.")
        @Column(columnDefinition = "TEXT")
        private String content;
    }

    public record PostsResponse(
            String title,
            Long postId,
            LocalDateTime createdAt) {
    }

    public record PostDetailResponse(
            String title,
            String content,
            Long postId,
            LocalDateTime createdAt,
            Long userId,
            String userName) {
    }


}