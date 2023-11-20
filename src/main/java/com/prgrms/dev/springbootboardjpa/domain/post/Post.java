package com.prgrms.dev.springbootboardjpa.domain.post;

import com.prgrms.dev.springbootboardjpa.domain.BaseEntity;
import com.prgrms.dev.springbootboardjpa.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "posts")
public class Post extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "title", length = 50)
    private String title;

    @Lob
    @Column(name = "content")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Builder
    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
