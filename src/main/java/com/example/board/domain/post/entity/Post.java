package com.example.board.domain.post.entity;

import com.example.board.domain.common.entity.BaseEntity;
import com.example.board.domain.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.Objects;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {

    public static final int DEFAULT_VIEW = 0;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder.Default
    @Column(name = "view")
    private int view = DEFAULT_VIEW;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void increaseView() {
        this.view += 1;
    }

    public boolean isSameMember(String email) {
        return member.isSameEmail(email);
    }
}
