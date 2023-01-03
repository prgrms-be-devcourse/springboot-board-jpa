package devcourse.board.domain.post.model;

import devcourse.board.domain.member.model.Member;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Post() {
    }

    public static Post create(Member member, String title, String content) {
        Post post = new Post();
        post.member = member;
        post.title = title;
        post.content = content;
        post.createdAt = LocalDateTime.now();

        return post;
    }

    public void updateTitleAndContent(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public String findOutWriterName() {
        return this.member.getName();
    }

    public boolean matchMember(Long memberId) {
        return this.member.matchId(memberId);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Member getMember() {
        return member;
    }
}
