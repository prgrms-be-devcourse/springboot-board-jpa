package com.example.springbootboardjpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(exclude = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post")
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(max = 50, message = "유효 글자 수를 초과하였습니다.")
    @Column(length = 50)
    private String title;

    @Lob
    @NotNull
    @Size(message = "유효 글자 수를 초과하였습니다.")
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    public Post(long id, String title, String content, User user) {
        this.id = id;
        this.title = blankCheckTitle(title);
        this.content = content;
        setUser(user);
    }

    public Post(String title, String content, User user) {
        this.title = blankCheckTitle(title);
        this.content = content;
        setUser(user);
    }

    private void setUser(User user) {
        this.user = user;
        user.getPosts().add(this);
    }

    private String blankCheckTitle(String title) {
        if (title.isBlank())
            return "(제목없음)";
        return title;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeTitle(String title) {
        this.title = title;
    }
}
