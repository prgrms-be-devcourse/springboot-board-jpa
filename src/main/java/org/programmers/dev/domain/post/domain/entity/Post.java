package org.programmers.dev.domain.post.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.programmers.dev.base.BaseEntity;
import org.programmers.dev.domain.user.domain.entity.User;
import org.programmers.dev.exception.PostValidationException;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(name = "content", nullable = false, length = 1000)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, nullable = false)
    private User user;

    @Builder
    private Post(Long id, String title, String content, User user, LocalDateTime createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = user.getName();
        this.createdAt = createdAt;
        setUser(user);
    }

    public void updateTitle(String title) {
        if (title.length() > 50) {
            throw new PostValidationException("제목은 50자를 넘을 수 없습니다.");
        }
        this.title = title;
    }

    public void updateContent(String content) {
        if (content.length() > 1000) {
            throw new PostValidationException("내용은 1000자를 넘을 수 없습니다.");
        }
        this.content = content;
    }

    private void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }
}
