package com.example.springbootboardjpa.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Table(name = "post")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // https://erjuer.tistory.com/106
public class Post extends DateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Builder(toBuilder = true)
    public Post(Long id, String title, String content, User user) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
    }

    public void setUser(User user) {
        if (Objects.nonNull(this.user)) {
            this.user.getPosts().remove(this);
        }
        this.user = user;
        user.getPosts().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return id.equals(post.id) && title.equals(post.title) && content.equals(post.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, content);
    }
}
