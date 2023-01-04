package com.example.springbootboardjpa.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@Getter
@ToString(exclude = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    public Post(String title, String content, User user) {
        checkNotNull(title, "제목은 Null 일 수 없습니다");
        checkNotNull(content, "내용은 Null 일 수 없습니다.");
        checkNotNull(user, "user는 Null 일 수 없습니다.");
        checkArgument(title.length() < 50, "제목은 50자 이하여야합니다.");

        this.title = checkBlankTitle(title);
        this.content = content;
        this.user = setUser(user);
    }

    private User setUser(User user) {
        user.getPosts().add(this);
        return user;
    }

    private String checkBlankTitle(String title) {
        if (title != null && title.isBlank())
            return "(제목없음)";
        return title;
    }

    public void changePost(String title, String content) {
        checkNotNull(title, "제목은 Null 일 수 없습니다");
        checkNotNull(content, "내용은 Null 일 수 없습니다.");
        checkArgument(title.length() < 50, "제목은 50자 이하여야합니다.");

        this.title = checkBlankTitle(title);
        this.content = content;
    }

}
