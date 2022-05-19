package com.kdt.prgrms.board.domain.post.entity;

import com.kdt.prgrms.board.domain.user.entity.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotNull
    private String title;

    @NotNull
    @Lob
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    protected Post() {

    }

    private Post(PostBuilder builder) {

        this.title = builder.title;
        this.content = builder.content;
        this.user = builder.user;
    }

    public static class PostBuilder {

        private String title;
        private String content;
        private User user;

        public PostBuilder title(String value) {

            this.title = value;
            return this;
        }

        public PostBuilder content(String value) {

            this.content = value;
            return this;
        }

        public PostBuilder user(User value) {

            this.user = value;
            return this;
        }

        public Post build() {

            return new Post(this);
        }
    }

    public static PostBuilder builders() {

        return new PostBuilder();
    }
}
