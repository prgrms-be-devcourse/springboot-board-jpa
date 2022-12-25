package com.prgrms.be.app.domain.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;

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

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class PostPageRequest {

        private static final int DEFAULT_SIZE = 10;
        private static final int MAX_SIZE = 20;

        private int page;
        private int size;
        private Sort.Direction direction;

        public PostPageRequest(int page, int size, Sort.Direction direction) {
            this.page = page <= 0 ? 1 : page;
            this.size = isRangeSize(size) ? size : DEFAULT_SIZE;
            this.direction = direction;
        }

        public PostPageRequest(int page, int size) {
            this(page, size, Sort.Direction.DESC);
        }

        private boolean isRangeSize(int size) {
            return size > 0 && size <= MAX_SIZE;
        }

        public org.springframework.data.domain.PageRequest of() {
            return org.springframework.data.domain.PageRequest.of(page - 1, size, direction, "createdAt");
        }
    }


}