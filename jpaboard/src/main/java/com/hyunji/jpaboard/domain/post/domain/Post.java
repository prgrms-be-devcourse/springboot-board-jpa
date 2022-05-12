package com.hyunji.jpaboard.domain.post.domain;

import com.hyunji.jpaboard.domain.user.domain.User;
import com.hyunji.jpaboard.model.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicUpdate
@Entity
public class Post extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private Post(String title, String content) {
        checkArgument(Strings.isNotBlank(title), "title 공백 불가");
        checkArgument(Strings.isNotBlank(content), "content 공백 불가");

        this.title = title;
        this.content = content;
    }

    public static Post create(String title, String content) {
        return new Post(title, content);
    }

    public void isWrittenBy(User user) {
        checkArgument(Objects.nonNull(user));

        this.user = user;
    }
}
