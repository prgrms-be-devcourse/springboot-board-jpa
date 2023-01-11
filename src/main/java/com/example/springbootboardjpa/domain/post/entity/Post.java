package com.example.springbootboardjpa.domain.post.entity;

import com.example.springbootboardjpa.domain.member.entity.Member;
import com.example.springbootboardjpa.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column()
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getPosts().remove(this);
        }
        this.member = member;
        member.getPosts().add(this);
        this.setCreatedBy(member.getId());
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
