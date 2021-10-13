package com.eunu.springbootboard.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name="post")
public class Post extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="title", nullable = false, length = 20)
    private String title;

    @Column(name="content", length = 2000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }

        this.user = user;
        user.getPosts().add(this);
    }

    public Post(Long id, String title, String content, LocalDateTime time, String userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.setCreatedAt(time);
        this.setCreatedBy(userId);
    }

    public Post(Long id, String title, String content, String userId) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.setCreatedAt(LocalDateTime.now());
        this.setCreatedBy(userId);
    }
}
