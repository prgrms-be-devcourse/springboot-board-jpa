package com.ys.board.domain.post.model;

import com.ys.board.domain.base.AbstractCreatedColumn;
import com.ys.board.domain.post.api.PostUpdateRequest;
import com.ys.board.domain.user.model.User;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends AbstractCreatedColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank
    @Size(min = 1)
    private String title;

    @Column(nullable = false)
    @NotBlank
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Post(User user, String title, String content) {
        validateTitle(title);
        validateContent(content);
        Assert.notNull(user, "user 는 null 이면 안됩니다.");
        this.title = title;
        this.content = content;
        this.user = user;
    }

    private void validateContent(String content) {
        Assert.hasText(content, "content 은 빈 값이면 안됩니다.");
    }

    private void validateTitle(String title) {
        Assert.hasText(title, "title 은 빈 값이면 안됩니다.");
    }

    public static Post create(User user, String title, String content) {
        return new Post(user, title, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Post post = (Post) o;
        return Objects.equals(id, post.id) && Objects.equals(title, post.title)
            && Objects.equals(content, post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content);
    }

    public void updateAll(PostUpdateRequest updateRequest) {
        validateTitle(updateRequest.getTitle());
        validateContent(updateRequest.getContent());
        this.title = updateRequest.getTitle();
        this.content = updateRequest.getContent();
    }

}
