package com.programmers.springbootboard.post.domain;

import com.programmers.springbootboard.common.domain.BaseEntity;
import com.programmers.springbootboard.member.domain.Member;
import com.programmers.springbootboard.post.domain.vo.Content;
import com.programmers.springbootboard.post.domain.vo.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Builder
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
public class Post extends BaseEntity<Long> {
    @Id
    @Column(name = "post_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_Id")
    private Member member;

    protected Post() {

    }

    public void addPost(Member member) {
        this.member = member;
        this.addCreatedAndLastModifiedMember(member.getId());
        member.getPosts().addPost(this);
    }

    public void update(Title title, Content content, Long id) {
        this.title = title;
        this.content = content;
        lastModifiedId(id);
    }


    public Long getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public Content getContent() {
        return content;
    }

    public Member getMember() {
        return member;
    }
}
