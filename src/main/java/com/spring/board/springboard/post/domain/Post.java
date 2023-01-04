package com.spring.board.springboard.post.domain;

import com.spring.board.springboard.user.domain.Member;
import com.spring.board.springboard.user.exception.AuthenticateException;
import jakarta.persistence.*;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "post")
@SequenceGenerator(
        name = "POST_SEQUENCE",
        sequenceName = "POST_SEQ",
        initialValue = 1,
        allocationSize = 1)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POST_SEQUENCE")
    private Integer id;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", nullable = false)
    @Lob
    private String content;

    @Column(name = "create_at", nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    protected Post() {
    }

    public Post(String title, String content, LocalDateTime createdAt, Member member) {
        validate(title, content, createdAt, member);
        this.createdAt = createdAt;
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public Integer getId() {
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

    public void change(String newTitle, String newContent) {
        validate(newTitle, newContent, this.createdAt, this.member);
        this.title = newTitle;
        this.content = newContent;
    }

    public String getMemberName() {
        return this.member.getName();
    }

    public void validateWriter(String requestEmail) {
        if (!Objects.equals(this.member.getEmail(), requestEmail)) {
            throw new AuthenticateException("접근 권한이 없습니다.");
        }
    }

    private void validate(String title, String content, LocalDateTime createdAt, Member member) {
        Assert.notNull(title, "제목을 입력해주세요.");
        Assert.notNull(content, "내용을 입력해주세요.");
        Assert.notNull(member, "작성자가 없을 수 없습니다.");
        Assert.notNull(createdAt, "작성된 시간은 반드시 있어야 합니다.");
    }
}
