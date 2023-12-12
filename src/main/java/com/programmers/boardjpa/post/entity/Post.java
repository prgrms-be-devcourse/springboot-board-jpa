package com.programmers.boardjpa.post.entity;

import com.programmers.boardjpa.global.common.BaseEntity;
import com.programmers.boardjpa.post.entity.vo.Title;
import com.programmers.boardjpa.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title")
    @Embedded
    private Title title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    @Builder
    public Post(String title, String content, User user) {
        this.title = new Title(title);
        this.content = content;
        this.user = user;
    }

    public void changeTitleAndContent(String title, String content) {
        this.title = this.title.changeTitle(title);
        this.content = content;
    }
}
