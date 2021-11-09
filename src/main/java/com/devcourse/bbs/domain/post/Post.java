package com.devcourse.bbs.domain.post;

import com.devcourse.bbs.domain.DateRecordedEntity;
import com.devcourse.bbs.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.Hibernate;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.Objects;

@Table(name = "post")
@Entity
@Getter
@NoArgsConstructor
@SuperBuilder
public class Post extends DateRecordedEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false, length = 65535)
    private String content;
    @ManyToOne(cascade = CascadeType.PERSIST, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public void changeTitle(@NonNull String title) {
        if(title.isBlank()) throw new IllegalArgumentException("Title string cannot be blank.");
        this.title = title;
    }

    public void changeContent(@NonNull String content) {
        if(content.isBlank()) throw new IllegalArgumentException("Content string cannot be blank.");
        this.content = content;
    }

    public PostDTO toDTO() {
        return new PostDTO(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
