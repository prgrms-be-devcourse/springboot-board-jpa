package com.kdt.springbootboard.domain.post;

import com.kdt.springbootboard.common.BaseEntity;
import com.kdt.springbootboard.domain.post.vo.Content;
import com.kdt.springbootboard.domain.post.vo.Title;
import com.kdt.springbootboard.domain.user.User;
import java.util.Objects;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private Title title;
    @Embedded
    private Content content;

    @ManyToOne(optional = true)
    @JoinColumn(name = "user_id")
    private User user;

    public void setUser(User user) { // 연관관계 편의 메서드
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    public void update(Title title, Content content) {
        this.title = title;
        this.content = content;
    }
}
