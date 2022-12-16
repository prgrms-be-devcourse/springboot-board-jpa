package com.prgrms.boardjpa.domain.post;

import com.prgrms.boardjpa.domain.BaseEntity;
import com.prgrms.boardjpa.domain.member.Member;

import javax.persistence.*;

@Entity
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(length = 30)
    private String title;
    @Lob
    private String content;

    @ManyToOne
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;
}
