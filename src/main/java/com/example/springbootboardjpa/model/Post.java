package com.example.springbootboardjpa.model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.validator.constraints.Length;

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

    public Post(String title, String content, User user) {
        this.title = checkBlankTitle(title);
        this.content = content;
        this.user = user != null ? setUser(user) : null;
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

    public void changePost(String title ,String content) {
        this.title = checkBlankTitle(title);
        this.content = content;
    }

}
