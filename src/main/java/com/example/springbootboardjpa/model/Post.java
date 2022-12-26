package com.example.springbootboardjpa.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;

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
    @Length(max = 50, message = "title 유효 글자 수를 초과하였습니다.")
    @Column(length = 50)
    private String title;

    @Lob
    @NotNull
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @Valid
    private User user;


    public Post(Long id, String title, String content, User user) {
        this.id = id;
        this.title = blankCheckTitle(title);
        this.content = content;
        this.user = user != null ? setUser(user) : null;
    }

    public Post(String title, String content, User user) {
        this(null,title,content,user);
    }

    private User setUser(User user) {
        user.getPosts().add(this);
        return user;
    }

    private String blankCheckTitle(String title) {
        if (title != null && title.isBlank())
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
