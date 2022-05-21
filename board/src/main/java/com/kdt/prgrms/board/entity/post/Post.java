package com.kdt.prgrms.board.entity.post;

import com.kdt.prgrms.board.entity.BaseEntity;
import com.kdt.prgrms.board.entity.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {

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

    public static PostBuilder builder() {

        return new PostBuilder();
    }

    public long getId() {

        return id;
    }

    public String getTitle() {

        return title;
    }

    public String getContent() {

        return content;
    }

    public User getUser() {

        return user;
    }
}
