package com.kdt.devboard.post.domain;

import com.kdt.devboard.BaseEntity;
import com.kdt.devboard.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)//ToDo : Mysql 사용시 identity로 변경
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name = "content", nullable = false)
    private String content;

    @Builder
    public Post(String createBy, String title, String content) {
        super(createBy);
        this.title = title;
        this.content = content;
    }

    public void changeInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public void setUser(User user) {
        if(Objects.nonNull(this.user)){
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

}
