package com.prgrms.board.post.domain;

import com.prgrms.board.common.BaseEntity;
import com.prgrms.board.user.domain.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "posts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {
    @Id
    @GeneratedValue
    private Long postId;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Post(String title, String content, User user) {
        this.title = title;
        this.content = content;
        setUser(user);
        setCreatedBy(user.getName());
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().getPosts().add(this);
    }

    public void changeTitle(String title) {
        if (!StringUtils.isBlank(title))
            this.title = title;
    }

    public void changeContent(String content) {
        if (!StringUtils.isBlank(content))
            this.content = content;
    }
}