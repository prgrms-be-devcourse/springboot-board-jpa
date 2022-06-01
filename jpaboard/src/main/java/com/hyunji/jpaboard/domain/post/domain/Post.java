package com.hyunji.jpaboard.domain.post.domain;

import com.hyunji.jpaboard.domain.user.domain.User;
import com.hyunji.jpaboard.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;

import javax.persistence.*;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    public Post(String title, String content, User user) {
        checkArgument(Strings.isNotBlank(title), "title 공백 불가");
        checkArgument(Strings.isNotBlank(content), "content 공백 불가");
        checkNotNull(user, "user null 불가");

        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void change(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
