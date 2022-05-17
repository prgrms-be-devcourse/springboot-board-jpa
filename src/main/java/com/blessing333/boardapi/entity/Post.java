package com.blessing333.boardapi.entity;

import com.blessing333.boardapi.entity.exception.PostCreateFailException;
import com.blessing333.boardapi.entity.exception.PostUpdateFailException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter(AccessLevel.PRIVATE)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User writer;

    public static Post createNewPost(String title, String content, User createBy) {
        Post post = new Post();
        post.setTitle(title);
        post.setContent(content);
        post.setWriter(createBy);
        return post;
    }

    public void changeTitle(String title) {
        if (title.length() < 2)
            throw new PostUpdateFailException("title length should over 2");
        this.title = title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    private void setTitle(String title) {
        if (title.length() < 2)
            throw new PostCreateFailException("title length should over 2");
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return id != null && Objects.equals(id, post.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
