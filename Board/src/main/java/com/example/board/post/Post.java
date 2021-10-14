package com.example.board.post;

import com.example.board.BaseEntity;
import com.example.board.member.Member;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Entity
@Table(name = "post")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;
    private String content;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id", referencedColumnName = "member_id")
    private Member member;

    protected Post(){}

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setMember(Member member){
        if(Objects.nonNull(this.member)){
            member.getPosts().remove(this);
        }
        this.member = member;
        member.getPosts().add(this);
        this.setCreatedBy(member.getName());
        this.setCreatedAt(LocalDateTime.now());
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
