package devcourse.board.domain.post.model;

import devcourse.board.domain.member.model.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private Member member;

    protected Post() {
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void createPost(Member member) {
        this.member = member;
        this.createdAt = LocalDateTime.now();
    }

    public void updateContents(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String findOutWriterName() {
        return this.member.getName();
    }
}
