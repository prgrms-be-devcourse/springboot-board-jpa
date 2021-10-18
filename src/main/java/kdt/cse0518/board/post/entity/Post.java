package kdt.cse0518.board.post.entity;

import kdt.cse0518.board.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
@Builder
@Getter
@AllArgsConstructor
public class Post {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @Column(name = "created_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @Column(name = "modified_at", columnDefinition = "TIMESTAMP")
    private LocalDateTime modifiedAt;

    @Column(name = "modified_by")
    private String modifiedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", referencedColumnName = "user_id")
    private User user;

    protected Post() {
    }

    public void setUser(final User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        user.addPost(this);
    }

    public Long update(final String title, final String content) {
        this.title = title;
        this.content = content;
        return this.postId;
    }
}
