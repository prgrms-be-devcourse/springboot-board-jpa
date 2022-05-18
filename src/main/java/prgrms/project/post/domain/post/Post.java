package prgrms.project.post.domain.post;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import prgrms.project.post.domain.BaseEntity;
import prgrms.project.post.domain.user.User;

import javax.persistence.*;
import java.util.Objects;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    @Column(name = "post_id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, length = 100)
    String title;

    @Lob
    @Column(nullable = false)
    String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder
    public Post(Long id, String title, String content, User user) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.assignUser(user);
    }

    public Post update(String title, String content) {
        this.title = title;
        this.content = content;

        return this;
    }

    private void assignUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        user.getPosts().add(this);
    }
}
