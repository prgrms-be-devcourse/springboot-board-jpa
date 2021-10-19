package com.poogle.board.model.post;

import com.poogle.board.model.BaseEntity;
import com.poogle.board.model.user.User;
import lombok.Getter;

import javax.persistence.*;
import java.util.Objects;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Getter
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    protected Post() {
    }

    public static Post of(String title, String content, String writer) {
        return new Post(title, content, writer);
    }

    private Post(String title, String content, String writer) {
        checkArgument(isNotEmpty(title), "Title must be provided.");
        checkArgument(
                content.length() >= 1 && content.length() <= 500,
                "Content length must be between 1 and 500 characters."
        );
        checkArgument(isNotEmpty(writer), "Writer must be provided.");
        this.title = title;
        this.content = content;
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    public void update(String title, String content) {
        checkArgument(isNotEmpty(title), "Title must be provided.");
        checkArgument(
                content.length() >= 1 && content.length() <= 500,
                "Content length must be between 1 and 500 characters."
        );
        this.title = title;
        this.content = content;
    }
}
