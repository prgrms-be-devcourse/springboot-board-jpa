package com.kdt.prgrms.board.entity.post;

import com.kdt.prgrms.board.entity.BaseEntity;
import com.kdt.prgrms.board.entity.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

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

    public boolean isSameUser(User user) {

        return this.user.equals(user);
    }

    public void updatePost(String title, String content) {

        this.title = title;
        this.content = content;
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

    public long getUserId() {

        return user.getId();
    }

    public String getUserName() {

        return user.getName();
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id == post.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
