package com.example.yiseul.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@NoArgsConstructor(access = PROTECTED)
public class Post extends BaseEntity {
    public static final int MAX_TITLE_LENGTH = 30;

    @Id
    @GeneratedValue
    private Long id;

    @Column(length = MAX_TITLE_LENGTH, nullable = false)
    private String title;

    @Column(columnDefinition = "text", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public Post(Member member, String title, String content) {
        validationTitle(title);
        this.title = title;
        this.content = content;
        setCreatedBy(member.getName());
    }

    public void updatePost(String title, String content) {
        this.title = title;
        this.content = content;
    }

    private void validationTitle(String title) {
        if (title.length() <= MAX_TITLE_LENGTH || title == null) {

            throw new IllegalArgumentException("제목은 50자 아니래 입력되어야만 합니다.");
        }
    }

}
