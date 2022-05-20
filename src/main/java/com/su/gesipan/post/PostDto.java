package com.su.gesipan.post;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public interface PostDto {

    @Data
    @NoArgsConstructor
    class Create {
        @NotNull
        private Long userId;
        @Size(min = 1, max = 100)
        private String title;
        @Size(max = 1500)
        private String content;

        @Builder
        public Create(Long userId, String title, String content) {
            this.userId = userId;
            this.title = title;
            this.content = content;
        }

        public static Create of(Long userId, String title, String content) {
            return new Create(userId, title, content);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Update {
        @Size(min = 1, max = 100)
        private String title;
        @Size(max = 1500)
        private String content;

        public static Update of(String title, String content) {
            return new Update(title, content);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    class Result {
        private Long id;
        private String title;
        private String content;
        private Long userId;

        public static Result of(Long id, String title, String content, Long userId) {
            return new Result(id, title, content, userId);
        }
    }

}
