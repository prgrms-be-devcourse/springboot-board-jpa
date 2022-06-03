package com.prgrms.work.controller.dto;

import com.prgrms.work.post.domain.Post;
import java.time.LocalDateTime;

public class PostResponse {

    public static class Posts {

        private long id;
        private String title;
        private String content;
        private String createdBy;
        private LocalDateTime createdAt;

        public Posts(long id, String title, String content, String createdBy, LocalDateTime createdAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.createdBy = createdBy;
            this.createdAt = createdAt;
        }

        public long getId() {return this.id;}

        public String getTitle() {
            return this.title;
        }

        public String getContent() {
            return this.content;
        }

        public String getCreatedBy() {
            return this.createdBy;
        }

        public LocalDateTime getCreatedAt() {
            return this.createdAt;
        }

        public static Posts of(Post post) {
            return new Posts(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedBy(),
                post.getCreatedAt());
        }

    }

    public static class PostDetail {

        private long id;
        private String title;
        private String content;
        private String name;
        private int age;
        private String hobby;
        private String createdBy;
        private LocalDateTime createdAt;

        public PostDetail(long id, String title, String content,
            String name, int age, String hobby, String createdBy, LocalDateTime createdAt) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.name = name;
            this.age = age;
            this.hobby = hobby;
            this.createdBy = createdBy;
            this.createdAt = createdAt;
        }

        public String getTitle() {
            return this.title;
        }

        public String getContent() {
            return this.content;
        }

        public String getName() {
            return this.name;
        }

        public int getAge() {
            return this.age;
        }

        public String getHobby() {
            return this.hobby;
        }

        public String getCreatedBy() {
            return this.createdBy;
        }

        public LocalDateTime getCreatedAt() {
            return this.createdAt;
        }

        public static PostDetail of(Post post) {
            return new PostDetail(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getUser().getName(),
                post.getUser().getAge(),
                post.getUser().getHobby(),
                post.getCreatedBy(),
                post.getCreatedAt()
            );
        }

    }

}
