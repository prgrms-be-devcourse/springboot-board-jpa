package com.kdt.devcourse.module.post.domain;

import com.kdt.devcourse.global.BaseEntity;
import com.kdt.devcourse.module.user.domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Entity
@Table(name = "posts")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
