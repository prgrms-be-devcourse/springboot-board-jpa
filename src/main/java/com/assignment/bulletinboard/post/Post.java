package com.assignment.bulletinboard.post;

import com.assignment.bulletinboard.BaseEntity;
import com.assignment.bulletinboard.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Builder
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 50)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    public void changeTitle(String title) {
        this.title = title;
        LocalDateTime now = LocalDateTime.now();
        setUpdatedAt(now);
    }

    public void changeContent(String content) {
        this.content = content;
        LocalDateTime now = LocalDateTime.now();
        setUpdatedAt(now);
    }

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private User user;

}
