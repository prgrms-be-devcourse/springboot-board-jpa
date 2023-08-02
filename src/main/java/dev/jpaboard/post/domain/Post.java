package dev.jpaboard.post.domain;

import dev.jpaboard.common.entity.BaseEntity;
import dev.jpaboard.common.exception.UnauthorizedAccessException;
import dev.jpaboard.user.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(length = 25, nullable = false)
    private String title;

    @Column(length = 5000, nullable = false)
    private String content;

    @Builder
    private Post(User user, String title, String content) {
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void checkAuthorize(User user) {
        if (!this.user.equals(user)) {
            throw new UnauthorizedAccessException();
        }
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Post post)) return false;
        return id.equals(post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
