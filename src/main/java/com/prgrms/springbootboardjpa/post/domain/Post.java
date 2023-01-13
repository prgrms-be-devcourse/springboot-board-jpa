package com.prgrms.springbootboardjpa.post.domain;

import com.prgrms.springbootboardjpa.user.domain.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "post")
public class Post {
    @Id()
    @GeneratedValue
    @Column(name = "post_id")
    private long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member createdBy;

    private String title;
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    protected Post() {
    }

    public Post(String title, String content, LocalDateTime createdAt) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public Post(Member createdBy, String title, String content, LocalDateTime createdAt) {
        this.createdBy = createdBy;
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
    }

    public void writeByMember(Member member) {
        this.createdBy = member;
        member.getPosts().add(this);
    }
    
    public void changePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

}
