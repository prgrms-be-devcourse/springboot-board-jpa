package com.prgrms.springbootboardjpa.post.domain;

import com.prgrms.springbootboardjpa.common.entity.BaseTimeEntity;
import com.prgrms.springbootboardjpa.member.domain.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "post")
public class Post extends BaseTimeEntity {
    @Id()
    @GeneratedValue
    @Column(name = "post_id")
    private long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdBy")
    private Member createdBy;

    private String title;
    private String content;

    protected Post() {
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public Post(Member createdBy, String title, String content) {
        this.createdBy = createdBy;
        this.title = title;
        this.content = content;
    }

    public void changePost(Post post) {
        this.title = post.getTitle();
        this.content = post.getContent();
    }

    public Long getMemberId() {
        return createdBy.getMemberId();
    }

}
